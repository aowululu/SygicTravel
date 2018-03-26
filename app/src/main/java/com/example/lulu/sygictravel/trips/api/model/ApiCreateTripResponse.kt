package com.example.lulu.sygictravel.trips.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiCreateTripResponse(
	val trip: ApiTripItemResponse
)
