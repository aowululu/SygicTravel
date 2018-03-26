package com.example.lulu.sygictravel.synchronization.services

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.synchronization.api.model.ApiChangesResponse
import com.example.lulu.sygictravel.synchronization.model.SynchronizationResult
import com.example.lulu.sygictravel.utils.DateTimeHelper
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

internal class SynchronizationService constructor(
	private val sharedPreferences: SharedPreferences,
	private val apiClient: SygicTravelApiClient,
	private val favoritesSynchronizationService: FavoritesSynchronizationService,
	private val tripsSynchronizationResult: TripsSynchronizationService
) {
	companion object {
		private const val SINCE_KEY = "sync.since"
	}

	var synchronizationCompletionHandler: ((result: SynchronizationResult) -> Unit)? = null
	private val lock = ReentrantLock()

	fun synchronize() {
		if (lock.isLocked) {
			return
		}

		thread(name = "stsdksync") {
			if (!lock.tryLock()) {
				return@thread
			}

			try {
				runSynchronization()
			} finally {
				lock.unlock()
			}
		}
	}

	private fun runSynchronization() {
		val result = SynchronizationResult()
		try {
			synchronizeWithResult(result)
		} catch (e: Exception) {
			result.success = false
			result.exception = e
		} finally {
			synchronizationCompletionHandler?.invoke(result)
		}
	}

	@SuppressLint("ApplySharedPref")
	private fun synchronizeWithResult(result: SynchronizationResult) {
		val since = sharedPreferences.getLong(SINCE_KEY, 0)
		val changesResponse = apiClient.getChanges(
			DateTimeHelper.timestampToDatetime(since)
		).execute().body()!!

		val changedTripIds = mutableListOf<String>()
		val deletedTripIds = mutableListOf<String>()
		val addedFavoriteIds = mutableListOf<String>()
		val deletedFavoriteIds = mutableListOf<String>()

		for (change in changesResponse.data?.changes ?: arrayListOf()) {
			when (change.type) {
				ApiChangesResponse.ChangeEntry.TYPE_TRIP -> {
					if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_UPDATED) {
						changedTripIds.add(change.id!!)
					} else if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_DELETED) {
						deletedTripIds.add(change.id!!)
					}
				}
				ApiChangesResponse.ChangeEntry.TYPE_FAVORITE -> {
					if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_UPDATED) {
						addedFavoriteIds.add(change.id!!)
					} else if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_DELETED) {
						deletedFavoriteIds.add(change.id!!)
					}
				}
				ApiChangesResponse.ChangeEntry.TYPE_SETTINGS -> {
				} // ignore
			}
		}

		tripsSynchronizationResult.sync(changedTripIds, deletedTripIds, result)
		favoritesSynchronizationService.sync(addedFavoriteIds, deletedFavoriteIds, result)

		val changesFetchedAt = DateTimeHelper.datetimeToTimestamp(changesResponse.server_timestamp)!!
		sharedPreferences.edit()
			.putLong(SINCE_KEY, changesFetchedAt)
			.commit()
	}

	fun clearUserData() {
		sharedPreferences.edit().remove(SINCE_KEY).apply()
	}
}
