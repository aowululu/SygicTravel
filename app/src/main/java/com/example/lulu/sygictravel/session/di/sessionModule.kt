package com.example.lulu.sygictravel.session.di

import android.content.SharedPreferences
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.squareup.moshi.Moshi
import com.example.lulu.sygictravel.favorites.facade.FavoritesFacade
import com.example.lulu.sygictravel.session.api.SygicSsoApiClient
import com.example.lulu.sygictravel.session.facade.SessionFacade
import com.example.lulu.sygictravel.session.service.AuthStorageService
import com.example.lulu.sygictravel.session.service.SessionService
import com.example.lulu.sygictravel.synchronization.facades.SynchronizationFacade
import com.example.lulu.sygictravel.trips.facades.TripsFacade

internal val sessionModule = Kodein.Module {
	bind<AuthStorageService>() with singleton {
		AuthStorageService(instance<SharedPreferences>())
	}

	bind<SessionService>() with singleton {
		SessionService(
			instance<SygicSsoApiClient>(),
			instance<AuthStorageService>(),
			instance<String>("clientId"),
			instance<Moshi>()
		)
	}

	bind<SessionFacade>() with singleton {
		val authFacade = SessionFacade(
			instance<SessionService>()
		)
		authFacade.onSignOut.add {
			instance<TripsFacade>().clearUserData()
			instance<FavoritesFacade>().clearUserData()
			instance<SynchronizationFacade>().clearUserData()
		}
		authFacade
	}
}
