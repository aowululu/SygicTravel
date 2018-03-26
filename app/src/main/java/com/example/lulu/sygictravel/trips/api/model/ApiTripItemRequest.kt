package com.example.lulu.sygictravel.trips.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiTripItemRequest(
	val name: String?,
	val base_version: Int,
	val updated_at: String,
	val privacy_level: String,
	val starts_on: String?,
	val is_deleted: Boolean,
	val destinations: List<String>,
	val days: List<ApiTripItemResponse.Day>
)
