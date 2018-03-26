package com.example.lulu.sygictravel.tours.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.tours.facade.ToursFacade
import com.example.lulu.sygictravel.tours.service.ToursService

internal val toursModule = Kodein.Module {
	bind<ToursService>() with singleton {
		ToursService(instance<SygicTravelApiClient>())
	}

	bind<ToursFacade>() with singleton { ToursFacade(instance<ToursService>()) }
}
