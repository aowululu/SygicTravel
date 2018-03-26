package com.example.lulu.sygictravel.trips.database.converters

import com.example.lulu.sygictravel.trips.model.Trip
import com.example.lulu.sygictravel.trips.model.TripDay
import com.example.lulu.sygictravel.trips.database.entities.TripDay as DbTripDay

internal class TripDayDbConverter {
	fun from(dbDay: DbTripDay, trip: Trip): TripDay {
		val day = TripDay(trip)
		day.note = dbDay.note
		return day
	}

	fun to(day: TripDay): DbTripDay {
		val dbDay = DbTripDay()
		dbDay.note = day.note
		dbDay.tripId = day.trip.id
		dbDay.dayIndex = day.getDayIndex()
		return dbDay
	}
}
