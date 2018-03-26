package com.example.lulu.sygictravel.trips.api

import com.example.lulu.sygictravel.trips.api.model.ApiTripItemResponse
import com.example.lulu.sygictravel.trips.model.TripDay
import com.example.lulu.sygictravel.trips.model.TripDayItem

internal class TripDayItemConverter constructor(
	private val tripItemTransportConverter: TripItemTransportConverter
) {
	fun fromApi(apiItem: ApiTripItemResponse.Day.DayItem, tripDay: TripDay): TripDayItem {
		val localItem = TripDayItem(apiItem.place_id, tripDay)
		localItem.startTime = apiItem.start_time
		localItem.duration = apiItem.duration
		localItem.note = apiItem.note
		localItem.transportFromPrevious = tripItemTransportConverter.fromApi(apiItem.transport_from_previous)
		return localItem
	}

	fun toApi(localItem: TripDayItem): ApiTripItemResponse.Day.DayItem {
		return ApiTripItemResponse.Day.DayItem(
			place_id = localItem.placeId,
			start_time = localItem.startTime,
			duration = localItem.duration,
			note = localItem.note,
			transport_from_previous = tripItemTransportConverter.toApi(localItem.transportFromPrevious)
		)
	}
}
