package com.example.lulu.sygictravel.places.api.model

import com.example.lulu.sygictravel.places.model.Place
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlacesListResponse(
	val places: List<ApiPlaceListItemResponse>
) {
	fun fromApi(): List<Place> {
		return places.map { it.fromApi() }
	}
}
