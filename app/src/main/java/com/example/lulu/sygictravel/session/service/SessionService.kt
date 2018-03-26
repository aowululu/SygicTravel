package com.example.lulu.sygictravel.session.service

import com.squareup.moshi.Moshi
import com.example.lulu.sygictravel.session.api.SygicSsoApiClient
import com.example.lulu.sygictravel.session.api.model.AuthenticationRequest
import com.example.lulu.sygictravel.session.api.model.ErrorResponse
import com.example.lulu.sygictravel.session.api.model.ResetPasswordRequest
import com.example.lulu.sygictravel.session.api.model.UserRegistrationRequest
import com.example.lulu.sygictravel.session.facade.AuthenticationResponseCode
import com.example.lulu.sygictravel.session.facade.RegistrationResponseCode
import com.example.lulu.sygictravel.session.facade.ResetPasswordResponseCode
import com.example.lulu.sygictravel.session.model.Session
import retrofit2.HttpException
import java.util.Date

internal class SessionService(
	private val sygicSsoClient: SygicSsoApiClient,
	private val authStorageService: AuthStorageService,
	private val clientId: String,
	private val moshi: Moshi
) {
	companion object {
		private const val DEVICE_PLATFORM = "android"
	}

	var sessionUpdateHandler: ((session: Session?) -> Unit)? = null

	fun authWithPassword(username: String, password: String): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			client_id = clientId,
			grant_type = "password",
			username = username,
			password = password,
			device_code = deviceId,
			device_platform = DEVICE_PLATFORM
		))
	}

	fun authWithGoogleToken(token: String): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			client_id = clientId,
			grant_type = "google",
			id_token = token,
			device_code = deviceId,
			device_platform = DEVICE_PLATFORM
		))
	}

	fun authWithFacebookToken(token: String): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			client_id = clientId,
			grant_type = "facebook",
			access_token = token,
			device_code = deviceId,
			device_platform = DEVICE_PLATFORM
		))
	}

	fun authWithJwtToken(token: String): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			client_id = clientId,
			grant_type = "external",
			token = token,
			device_code = deviceId,
			device_platform = DEVICE_PLATFORM
		))
	}

	fun authWithDeviceId(): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			client_id = clientId,
			grant_type = "client_credentials",
			device_code = deviceId,
			device_platform = DEVICE_PLATFORM
		))
	}

	fun register(email: String, password: String, name: String): RegistrationResponseCode {
		val userRegistrationRequest = UserRegistrationRequest(
			username = email,
			email = email,
			password = password,
			name = name,
			email_is_verified = true
		)
		var clientSession = authStorageService.getClientSession() ?: initClientSession()

		var response = sygicSsoClient.registerUser("Bearer $clientSession", userRegistrationRequest).execute()
		if (response.code() == 401) { // client session is expired, let's reinitialize it
			clientSession = initClientSession()
			response = sygicSsoClient.registerUser("Bearer $clientSession", userRegistrationRequest).execute()
		}

		if (response.isSuccessful) {
			return RegistrationResponseCode.OK

		} else if (response.code() == 409) {
			return RegistrationResponseCode.ERROR_ALREADY_REGISTERED

		} else {
			val errorResponse = moshi.adapter(ErrorResponse::class.java).fromJsonValue(response.errorBody()?.string())
			return when (errorResponse?.type) {
				"validation.password.min_length" -> RegistrationResponseCode.ERROR_PASSWORD_MIN_LENGTH
				"validation.username.min_length", "validation.email.invalid_format" -> RegistrationResponseCode.ERROR_EMAIL_INVALID_FORMAT
				else -> RegistrationResponseCode.ERROR
			}
		}
	}

	fun resetPassword(email: String): ResetPasswordResponseCode {
		val resetPasswordRequest = ResetPasswordRequest(email)
		var clientSession = authStorageService.getClientSession() ?: initClientSession()

		var response = sygicSsoClient.resetPassword("Bearer $clientSession", resetPasswordRequest).execute()
		if (response.code() == 401) {
			clientSession = initClientSession()
			response = sygicSsoClient.resetPassword("Bearer $clientSession", resetPasswordRequest).execute()
		}

		return when {
			response.isSuccessful -> ResetPasswordResponseCode.OK
			response.code() == 404 -> ResetPasswordResponseCode.ERROR_USER_NOT_FOUND
			response.code() == 422 -> ResetPasswordResponseCode.ERROR_EMAIL_INVALID_FORMAT
			else -> ResetPasswordResponseCode.ERROR
		}
	}

	fun getUserSession(): Session? {
		val accessToken = authStorageService.getUserSession() ?: return null
		val refreshToken = authStorageService.getRefreshToken() ?: return null
		val refreshTimeExpiration = authStorageService.getSuggestedRefreshTime()

		if (Date().time >= refreshTimeExpiration) {
			refreshToken(refreshToken)
		}

		return Session(
			accessToken = accessToken,
			expiresAt = Date(authStorageService.getExpirationTime())
		)
	}

	fun logout() {
		authStorageService.setUserSession(null)
		authStorageService.setExpirationTime(0)
		authStorageService.setRefreshToken(null)
	}

	private fun authenticate(authRequest: AuthenticationRequest): AuthenticationResponseCode {
		val response = sygicSsoClient.authenticate(authRequest).execute()
		if (response.isSuccessful) {
			val userSession = response.body()!!
			authStorageService.setUserSession(userSession.access_token)
			authStorageService.setExpirationTime(userSession.expires_in)
			authStorageService.setRefreshToken(userSession.refresh_token)
			return AuthenticationResponseCode.OK

		} else if (response.code() == 401) {
			return AuthenticationResponseCode.ERROR_INVALID_CREDENTIALS

		} else {
			return AuthenticationResponseCode.ERROR
		}
	}

	private fun refreshToken(refreshToken: String) {
		authenticate(AuthenticationRequest(
			client_id = clientId,
			grant_type = "refresh_token",
			refresh_token = refreshToken
		))
	}

	private fun initClientSession(): String {
		val request = sygicSsoClient.authenticate(AuthenticationRequest(
			client_id = clientId,
			grant_type = "client_credentials"
		))
		val response = request.execute()
		return if (response.isSuccessful) {
			val accessToken = response.body()!!.access_token
			authStorageService.setClientSession(accessToken)
			accessToken
		} else throw HttpException(response)
	}
}
