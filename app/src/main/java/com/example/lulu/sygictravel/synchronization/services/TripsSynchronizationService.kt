package com.example.lulu.sygictravel.synchronization.services

import com.example.lulu.sygictravel.common.ApiResponseException
import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.synchronization.model.SynchronizationResult
import com.example.lulu.sygictravel.synchronization.model.TripConflictInfo
import com.example.lulu.sygictravel.synchronization.model.TripConflictResolution
import com.example.lulu.sygictravel.trips.api.TripConverter
import com.example.lulu.sygictravel.trips.api.model.ApiTripItemResponse
import com.example.lulu.sygictravel.trips.api.model.ApiUpdateTripResponse
import com.example.lulu.sygictravel.trips.model.Trip
import com.example.lulu.sygictravel.trips.services.TripsService
import com.example.lulu.sygictravel.utils.DateTimeHelper
import retrofit2.Response
import java.io.IOException
import java.util.Date

internal class TripsSynchronizationService constructor(
	private val apiClient: SygicTravelApiClient,
	private val tripConverter: TripConverter,
	private val tripsService: TripsService
) {
	var tripIdUpdateHandler: ((oldTripId: String, newTripId: String) -> Unit)? = null
	var tripUpdateConflictHandler: ((conflictInfo: TripConflictInfo) -> TripConflictResolution)? = null

	fun sync(changedTripIds: List<String>, deletedTripIds: List<String>, syncResult: SynchronizationResult) {
		val changedTrips = if (changedTripIds.isNotEmpty()) {
			val changesResponse = apiClient.getTrips(changedTripIds.joinToString("|")).execute()
			checkResponse(changesResponse)
			changesResponse.body()!!.data!!.trips
		} else {
			listOf()
		}

		for (deletedTripId in deletedTripIds) {
			tripsService.deleteTrip(deletedTripId)
		}

		syncResult.changedTripIds.addAll(deletedTripIds)

		for (trip in changedTrips) {
			syncApiChangedTrip(trip, syncResult)
		}

		syncLocalChangedTrips(syncResult)
	}

	private fun syncApiChangedTrip(apiTrip: ApiTripItemResponse, syncResult: SynchronizationResult) {
		val localTrip = tripsService.getTrip(apiTrip.id)

		if (localTrip == null) {
			createLocalTrip(apiTrip, syncResult)

		} else if (!localTrip.isChanged) {
			updateLocalTrip(apiTrip, syncResult)

		} else {
			// we throw away the server data and try to push our changes first
			// server will try a data merge
			updateServerTrip(localTrip, syncResult)
		}
	}

	private fun syncLocalChangedTrips(syncResult: SynchronizationResult) {
		tripsService.findAllChanged().forEach { trip ->
			if (trip.isLocal()) {
				createServerTrip(trip, syncResult)
			} else {
				updateServerTrip(trip, syncResult)
			}
		}
	}

	private fun createLocalTrip(apiTrip: ApiTripItemResponse, syncResult: SynchronizationResult) {
		val localTrip = tripConverter.fromApi(apiTrip)
		tripsService.saveTrip(localTrip)
		syncResult.changedTripIds.add(localTrip.id)
	}

	private fun updateLocalTrip(apiTrip: ApiTripItemResponse, syncResult: SynchronizationResult) {
		val localTrip = tripConverter.fromApi(apiTrip)
		tripsService.saveTrip(localTrip)
		syncResult.changedTripIds.add(localTrip.id)
	}

	private fun createServerTrip(localTrip: Trip, syncResult: SynchronizationResult) {
		val createResponse = apiClient.createTrip(tripConverter.toApi(localTrip)).execute()
		checkResponse(createResponse)
		val trip = createResponse.body()!!.data!!.trip
		syncResult.cratedTripIdsMapping[localTrip.id] = trip.id
		tripsService.replaceTripId(localTrip, trip.id)
		tripsService.saveTrip(tripConverter.fromApi(trip))
		tripIdUpdateHandler?.invoke(localTrip.id, trip.id)
	}

	private fun updateServerTrip(localTrip: Trip, syncResult: SynchronizationResult) {
		val updateResponse = apiClient.updateTrip(localTrip.id, tripConverter.toApi(localTrip)).execute()

		if (updateResponse.code() == 404) {
			tripsService.deleteTrip(localTrip.id)
			syncResult.changedTripIds.add(localTrip.id)
			return
		}

		checkResponse(updateResponse)
		val data = updateResponse.body()!!.data!!
		var apiTripData = data.trip
		when (data.conflict_resolution) {
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_IGNORED -> {
				val conflictHandler = tripUpdateConflictHandler
				val conflictResolution = when (conflictHandler) {
					null -> TripConflictResolution.USE_SERVER_VERSION
					else -> {
						val conflictInfo = TripConflictInfo(
							localTrip = localTrip,
							remoteTrip = tripConverter.fromApi(apiTripData),
							remoteTripUserName = data.conflict_info!!.last_user_name,
							remoteTripUpdatedAt = Date(DateTimeHelper.datetimeToTimestamp(data.conflict_info.last_updated_at)!! * 1000)
						)
						conflictHandler.invoke(conflictInfo)
					}
				}
				when (conflictResolution) {
					TripConflictResolution.NO_ACTION -> {
						// do nothing and let user choose when he will use the app
						return
					}
					TripConflictResolution.USE_LOCAL_VERSION -> {
						localTrip.updatedAt = DateTimeHelper.now()
						// if request fails, user will not have to do the decision again
						tripsService.saveTrip(localTrip)
						val repeatedUpdateResponse = apiClient.updateTrip(localTrip.id, tripConverter.toApi(localTrip)).execute()
						checkResponse(repeatedUpdateResponse)
						apiTripData = repeatedUpdateResponse.body()!!.data!!.trip
						updateLocalTrip(apiTripData, syncResult)
					}
					TripConflictResolution.USE_SERVER_VERSION -> {
						updateLocalTrip(apiTripData, syncResult)
					}
				}
			}
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_MERGED -> {
				updateLocalTrip(apiTripData, syncResult)
			}
			else -> {
				// do not track a change, restore isChanged to false
				tripsService.saveTrip(tripConverter.fromApi(apiTripData))
			}
		}
	}

	private fun checkResponse(response: Response<*>) {
		if (!response.isSuccessful) {
			if (response.code() < 500) {
				val exception = ApiResponseException("Invalid request: " + response.code())
				exception.response = response
				throw exception
			} else {
				throw IOException("Server error: " + response.code())
			}
		}
	}
}
