package com.example.lulu.sygictravel

import android.content.Context
import com.github.salomonbrys.kodein.instance
import com.example.lulu.sygictravel.common.di.KodeinSetup
import com.example.lulu.sygictravel.directions.facades.DirectionsFacade
import com.example.lulu.sygictravel.events.facades.EventsFacade
import com.example.lulu.sygictravel.favorites.facade.FavoritesFacade
import com.example.lulu.sygictravel.places.facade.PlacesFacade
import com.example.lulu.sygictravel.session.facade.SessionFacade
import com.example.lulu.sygictravel.synchronization.facades.SynchronizationFacade
import com.example.lulu.sygictravel.tours.facade.ToursFacade
import com.example.lulu.sygictravel.trips.facades.TripsFacade

/**
 * Provides public methods for requesting API.
 */
class Sdk(
	applicationContext: Context,
	sdkConfig: SdkConfig
) {
	val directionsFacade: DirectionsFacade by lazy {
		kodein.instance<DirectionsFacade>()
	}

	val eventsFacade: EventsFacade by lazy {
		kodein.instance<EventsFacade>()
	}

	val favoritesFacade: FavoritesFacade by lazy {
		checkUserDataSupport("Favorites")
		kodein.instance<FavoritesFacade>()
	}

	val placesFacade: PlacesFacade by lazy {
		kodein.instance<PlacesFacade>()
	}

	val sessionFacade: SessionFacade by lazy {
		checkUserDataSupport("Session")
		kodein.instance<SessionFacade>()
	}

	val synchronizationFacade: SynchronizationFacade by lazy {
		checkUserDataSupport("Synchronization")
		kodein.instance<SynchronizationFacade>()
	}

	val toursFacade: ToursFacade by lazy {
		kodein.instance<ToursFacade>()
	}

	val tripsFacade: TripsFacade by lazy {
		checkUserDataSupport("Trips")
		kodein.instance<TripsFacade>()
	}

	private val kodein = KodeinSetup.setupKodein(applicationContext, sdkConfig)

	private fun checkUserDataSupport(module: String) {
		if (!kodein.instance<Boolean>("userDataSupported")) {
			throw IllegalStateException("$module module can be used only with enabled user-data support. To enable it, configure Sdk with clientId.")
		}
	}
}
