package com.example.lulu.sygictravel.trips.database.converters

import com.example.lulu.sygictravel.trips.model.TripItemTransport
import com.example.lulu.sygictravel.trips.database.entities.TripDayItemTransport as DbTripItemTransport

internal class TripDayItemTransportDbConverter {
	fun from(dbTransport: DbTripItemTransport?): TripItemTransport? {
		dbTransport ?: return null
		val transport = TripItemTransport(dbTransport.mode)
		transport.avoid = dbTransport.avoid
		transport.startTime = dbTransport.startTime
		transport.duration = dbTransport.duration
		transport.note = dbTransport.note
		transport.waypoints = dbTransport.waypoints
		return transport
	}

	fun to(transport: TripItemTransport?): DbTripItemTransport? {
		transport ?: return null
		val dbTransport = DbTripItemTransport()
		dbTransport.mode = transport.mode
		dbTransport.avoid = ArrayList(transport.avoid)
		dbTransport.startTime = transport.startTime
		dbTransport.duration = transport.duration
		dbTransport.note = transport.note
		dbTransport.waypoints = ArrayList(transport.waypoints)
		return dbTransport
	}
}
