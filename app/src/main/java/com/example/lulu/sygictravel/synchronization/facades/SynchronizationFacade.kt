package com.example.lulu.sygictravel.synchronization.facades

import com.example.lulu.sygictravel.favorites.service.FavoriteService
import com.example.lulu.sygictravel.synchronization.services.SynchronizationService
import com.example.lulu.sygictravel.trips.services.TripsService
import com.example.lulu.sygictravel.utils.checkNotRunningOnMainThread

/**
 * Synchronization facade handles synchronization of user data (favorites, trips) with the server.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class SynchronizationFacade internal constructor(
	private val synchronizationService: SynchronizationService,
	private val tripsService: TripsService,
	private val favoritesService: FavoriteService
) {
	fun synchronize() {
		return synchronizationService.synchronize()
	}

	fun hasChangesToSynchronize(): Boolean {
		checkNotRunningOnMainThread()
		return tripsService.hasChangesToSynchronize()
			|| favoritesService.hasChangesToSynchronize()
	}

	internal fun clearUserData() {
		checkNotRunningOnMainThread()
		synchronizationService.clearUserData()
	}
}
