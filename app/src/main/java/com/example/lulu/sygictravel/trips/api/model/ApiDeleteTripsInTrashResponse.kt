package com.example.lulu.sygictravel.trips.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiDeleteTripsInTrashResponse(
	val deleted_trip_ids: List<String>
)
