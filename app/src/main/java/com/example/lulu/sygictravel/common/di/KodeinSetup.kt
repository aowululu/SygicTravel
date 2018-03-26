package com.example.lulu.sygictravel.common.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import com.github.salomonbrys.kodein.with
import com.example.lulu.sygictravel.SdkConfig
import com.example.lulu.sygictravel.common.database.di.dbModule
import com.example.lulu.sygictravel.directions.di.directionsModule
import com.example.lulu.sygictravel.events.di.eventsModule
import com.example.lulu.sygictravel.favorites.di.favoritesModule
import com.example.lulu.sygictravel.places.di.placesModule
import com.example.lulu.sygictravel.session.di.sessionModule
import com.example.lulu.sygictravel.synchronization.di.synchronizationModule
import com.example.lulu.sygictravel.tours.di.toursModule
import com.example.lulu.sygictravel.trips.di.tripsModule

internal object KodeinSetup {
	fun setupKodein(
		applicationContext: Context,
		sdkConfig: SdkConfig
	) = Kodein {
		constant("userDataSupported") with (sdkConfig.clientId != null)
		constant("clientId") with (sdkConfig.clientId ?: "")
		constant("apiKey") with sdkConfig.apiKey
		constant("debugMode") with sdkConfig.debugMode
		constant("sygicAuthUrl") with sdkConfig.sygicAuthUrl
		constant("sygicTravelApiUrl") with sdkConfig.sygicTravelApiUrl

		bind<Context>() with singleton { applicationContext }

		import(sessionModule)
		import(dbModule)
		import(directionsModule)
		import(eventsModule)
		import(favoritesModule)
		import(generalModule)
		import(placesModule)
		import(sygicAuthApiModule)
		import(sygicTravelApiModule)
		import(synchronizationModule)
		import(toursModule)
		import(tripsModule)
	}
}
