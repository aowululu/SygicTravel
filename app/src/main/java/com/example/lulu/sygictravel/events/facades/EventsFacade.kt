package com.example.lulu.sygictravel.events.facades

import com.example.lulu.sygictravel.session.model.Session
import com.example.lulu.sygictravel.session.service.SessionService
import com.example.lulu.sygictravel.synchronization.model.SynchronizationResult
import com.example.lulu.sygictravel.synchronization.model.TripConflictInfo
import com.example.lulu.sygictravel.synchronization.model.TripConflictResolution
import com.example.lulu.sygictravel.synchronization.services.SynchronizationService
import com.example.lulu.sygictravel.synchronization.services.TripsSynchronizationService

/**
 * Events facade provides an centralized access point for subscribing to SDK's events.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class EventsFacade internal constructor(
	private val sessionService: () -> SessionService,
	private val tripsSynchronizationService: () -> TripsSynchronizationService,
	private val synchronizationService: () -> SynchronizationService
) {
	var sessionUpdateHandler: ((session: Session?) -> Unit)?
		get() {
			return sessionService().sessionUpdateHandler
		}
		set(value) {
			sessionService().sessionUpdateHandler = value
		}

	var tripIdUpdateHandler: ((oldTripId: String, newTripId: String) -> Unit)?
		get() {
			return tripsSynchronizationService().tripIdUpdateHandler
		}
		set(value) {
			tripsSynchronizationService().tripIdUpdateHandler = value
		}

	var tripUpdateConflictHandler: ((conflictInfo: TripConflictInfo) -> TripConflictResolution)?
		get() {
			return tripsSynchronizationService().tripUpdateConflictHandler
		}
		set(value) {
			tripsSynchronizationService().tripUpdateConflictHandler = value
		}

	var synchronizationCompletionHandler: ((result: SynchronizationResult) -> Unit)?
		get() {
			return synchronizationService().synchronizationCompletionHandler
		}
		set(value) {
			synchronizationService().synchronizationCompletionHandler = value
		}
}
