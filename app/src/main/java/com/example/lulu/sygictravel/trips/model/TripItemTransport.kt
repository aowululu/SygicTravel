package com.example.lulu.sygictravel.trips.model

import com.example.lulu.sygictravel.directions.model.DirectionAvoid
import com.example.lulu.sygictravel.directions.model.DirectionMode

class TripItemTransport(
	var mode: DirectionMode
) {
	var avoid = listOf<DirectionAvoid>()
	var startTime: Int? = null
	var duration: Int? = null
	var note: String? = null
	var waypoints = listOf<TripItemTransportWaypoint>()
}
