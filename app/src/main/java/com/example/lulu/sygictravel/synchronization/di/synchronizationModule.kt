package com.example.lulu.sygictravel.synchronization.di

import android.content.SharedPreferences
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.favorites.service.FavoriteService
import com.example.lulu.sygictravel.synchronization.facades.SynchronizationFacade
import com.example.lulu.sygictravel.synchronization.services.FavoritesSynchronizationService
import com.example.lulu.sygictravel.synchronization.services.SynchronizationService
import com.example.lulu.sygictravel.synchronization.services.TripsSynchronizationService
import com.example.lulu.sygictravel.trips.api.TripConverter
import com.example.lulu.sygictravel.trips.services.TripsService

internal val synchronizationModule = Kodein.Module {
	bind<SynchronizationFacade>() with singleton {
		SynchronizationFacade(
			instance<SynchronizationService>(),
			instance<TripsService>(),
			instance<FavoriteService>()
		)
	}
	bind<SynchronizationService>() with singleton {
		SynchronizationService(
			instance<SharedPreferences>(),
			instance<SygicTravelApiClient>(),
			instance<FavoritesSynchronizationService>(),
			instance<TripsSynchronizationService>()
		)
	}
	bind<TripsSynchronizationService>() with singleton {
		TripsSynchronizationService(
			instance<SygicTravelApiClient>(),
			instance<TripConverter>(),
			instance<TripsService>()
		)
	}
	bind<FavoritesSynchronizationService>() with singleton {
		FavoritesSynchronizationService(
			instance<SygicTravelApiClient>(),
			instance<FavoriteService>()
		)
	}
}
