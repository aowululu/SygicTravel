package com.example.lulu.sygictravel.session.api

import com.example.lulu.sygictravel.session.api.model.AuthenticationRequest
import com.example.lulu.sygictravel.session.api.model.ResetPasswordRequest
import com.example.lulu.sygictravel.session.api.model.SessionResponse
import com.example.lulu.sygictravel.session.api.model.UserRegistrationRequest
import com.example.lulu.sygictravel.session.api.model.UserRegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface SygicSsoApiClient {
	@Headers("Content-Type: application/json")
	@POST("/oauth2/token")
	fun authenticate(
		@Body authenticationRequest: AuthenticationRequest
	): Call<SessionResponse>

	@Headers("Content-Type: application/json")
	@POST("/user/register")
	fun registerUser(
		@Header("Authorization") accessToken: String,
		@Body userAuthRequestBody: UserRegistrationRequest
	): Call<UserRegistrationResponse>

	@Headers("Content-Type: application/json")
	@POST("/user/reset-password")
	fun resetPassword(
		@Header("Authorization") accessToken: String,
		@Body resetPasswordRequest: ResetPasswordRequest
	): Call<Void>
}
