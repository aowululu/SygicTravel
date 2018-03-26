package com.example.lulu.sygictravel.places.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.places.facade.PlacesFacade
import com.example.lulu.sygictravel.places.service.PlacesService

internal val placesModule = Kodein.Module {
	bind<PlacesService>() with singleton {
		PlacesService(instance<SygicTravelApiClient>())
	}

	bind<PlacesFacade>() with singleton { PlacesFacade(instance<PlacesService>()) }
}
