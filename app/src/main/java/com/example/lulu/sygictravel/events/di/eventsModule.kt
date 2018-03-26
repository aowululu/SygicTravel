package com.example.lulu.sygictravel.events.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.example.lulu.sygictravel.session.service.SessionService
import com.example.lulu.sygictravel.events.facades.EventsFacade
import com.example.lulu.sygictravel.synchronization.services.SynchronizationService
import com.example.lulu.sygictravel.synchronization.services.TripsSynchronizationService

internal val eventsModule = Kodein.Module {
	bind<EventsFacade>() with singleton {
		EventsFacade(
			{ instance<SessionService>() },
			{ instance<TripsSynchronizationService>() },
			{ instance<SynchronizationService>() }
		)
	}
}
