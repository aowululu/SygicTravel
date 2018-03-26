package com.example.lulu.sygictravel.trips.database.entities

import android.arch.persistence.room.ColumnInfo
import com.example.lulu.sygictravel.directions.model.DirectionAvoid
import com.example.lulu.sygictravel.directions.model.DirectionMode
import com.example.lulu.sygictravel.trips.model.TripItemTransportWaypoint

internal class TripDayItemTransport {
	@ColumnInfo
	lateinit var mode: DirectionMode

	@ColumnInfo
	var avoid: ArrayList<DirectionAvoid> = arrayListOf()

	@ColumnInfo(name = "start_time")
	var startTime: Int? = null

	@ColumnInfo
	var duration: Int? = null

	@ColumnInfo
	var note: String? = null

	@ColumnInfo
	var waypoints: ArrayList<TripItemTransportWaypoint> = arrayListOf()
}
