package com.example.lulu.sygictravel.trips.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.common.database.Database
import com.example.lulu.sygictravel.trips.api.TripConverter
import com.example.lulu.sygictravel.trips.api.TripDayConverter
import com.example.lulu.sygictravel.trips.api.TripDayItemConverter
import com.example.lulu.sygictravel.trips.api.TripItemTransportConverter
import com.example.lulu.sygictravel.trips.database.converters.TripDayDbConverter
import com.example.lulu.sygictravel.trips.database.converters.TripDayItemDbConverter
import com.example.lulu.sygictravel.trips.database.converters.TripDayItemTransportDbConverter
import com.example.lulu.sygictravel.trips.database.converters.TripDbConverter
import com.example.lulu.sygictravel.trips.database.daos.TripDayItemsDao
import com.example.lulu.sygictravel.trips.database.daos.TripDaysDao
import com.example.lulu.sygictravel.trips.database.daos.TripsDao
import com.example.lulu.sygictravel.trips.facades.TripsFacade
import com.example.lulu.sygictravel.trips.services.TripsService

internal val tripsModule = Kodein.Module {
	bind<TripsFacade>() with singleton { TripsFacade(instance<TripsService>()) }
	bind<TripsService>() with singleton {
		TripsService(
			instance<SygicTravelApiClient>(),
			instance<TripsDao>(),
			instance<TripDaysDao>(),
			instance<TripDayItemsDao>(),
			instance<TripDbConverter>(),
			instance<TripDayDbConverter>(),
			instance<TripDayItemDbConverter>()
		)
	}
	bind<TripsDao>() with singleton { instance<Database>().tripsDao() }
	bind<TripDaysDao>() with singleton { instance<Database>().tripDaysDao() }
	bind<TripDayItemsDao>() with singleton { instance<Database>().tripDayItemsDao() }
	bind<TripConverter>() with singleton { TripConverter(instance<TripDayConverter>()) }
	bind<TripDayConverter>() with singleton { TripDayConverter(instance<TripDayItemConverter>()) }
	bind<TripDayItemConverter>() with singleton { TripDayItemConverter(instance<TripItemTransportConverter>()) }
	bind<TripItemTransportConverter>() with singleton { TripItemTransportConverter() }
	bind<TripDbConverter>() with singleton { TripDbConverter() }
	bind<TripDayDbConverter>() with singleton { TripDayDbConverter() }
	bind<TripDayItemDbConverter>() with singleton { TripDayItemDbConverter(instance<TripDayItemTransportDbConverter>()) }
	bind<TripDayItemTransportDbConverter>() with singleton { TripDayItemTransportDbConverter() }
}
