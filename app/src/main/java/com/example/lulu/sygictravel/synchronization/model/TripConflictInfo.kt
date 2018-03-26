package com.example.lulu.sygictravel.synchronization.model

import com.example.lulu.sygictravel.trips.model.Trip
import java.util.Date

data class TripConflictInfo(
	val localTrip: Trip,
	val remoteTrip: Trip,
	val remoteTripUserName: String,
	val remoteTripUpdatedAt: Date
)
