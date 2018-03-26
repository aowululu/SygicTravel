package com.example.lulu.sygictravel.trips.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiUpdateTripResponse(
	val trip: ApiTripItemResponse,
	val conflict_resolution: String?,
	val conflict_info: ConflictInfo?
) {
	companion object {
		const val CONFLICT_RESOLUTION_IGNORED = "ignored"
		const val CONFLICT_RESOLUTION_MERGED = "merged"
		const val CONFLICT_RESOLUTION_OVERRODE = "overrode"
		val NO_CONFLICT = null
	}

	class ConflictInfo(
		val last_user_name: String,
		val last_updated_at: String
	)
}
