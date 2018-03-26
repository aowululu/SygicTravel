package com.example.lulu.sygictravel.trips.model

class TripDay(
	val trip: Trip
) {
	var note: String? = null
	var itinerary = listOf<TripDayItem>()

	fun getDayIndex(): Int {
		return trip.days.indexOf(this)
	}

	fun getPlaceIds(): Set<String> {
		return itinerary.map { it.placeId }.toSet()
	}
}
