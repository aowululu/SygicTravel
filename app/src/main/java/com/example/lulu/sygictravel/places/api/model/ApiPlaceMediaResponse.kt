package com.example.lulu.sygictravel.places.api.model

import com.example.lulu.sygictravel.places.model.media.Medium
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlaceMediaResponse(
	var media: List<ApiMediumResponse>
) {
	fun fromApi(): List<Medium> {
		return media.map { it.fromApi() }
	}
}
