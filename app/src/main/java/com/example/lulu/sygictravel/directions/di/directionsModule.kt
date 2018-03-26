package com.example.lulu.sygictravel.directions.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.directions.facades.DirectionsFacade
import com.example.lulu.sygictravel.directions.services.ApiDirectionsService
import com.example.lulu.sygictravel.directions.services.CacheService
import com.example.lulu.sygictravel.directions.services.DirectionsService
import com.example.lulu.sygictravel.directions.services.NaiveDirectionsService

internal val directionsModule = Kodein.Module {
	bind<ApiDirectionsService>() with singleton {
		ApiDirectionsService(
			instance<SygicTravelApiClient>(),
			instance<NaiveDirectionsService>()
		)
	}

	bind<NaiveDirectionsService>() with singleton {
		NaiveDirectionsService()
	}

	bind<CacheService>() with singleton {
		CacheService(instance<Context>())
	}

	bind<DirectionsService>() with singleton {
		DirectionsService(
			instance<ApiDirectionsService>(),
			instance<NaiveDirectionsService>(),
			instance<CacheService>()
		)
	}

	bind<DirectionsFacade>() with singleton {
		DirectionsFacade(instance<DirectionsService>())
	}
}
