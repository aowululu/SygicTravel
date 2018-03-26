package com.example.lulu.sygictravel.places.api.model

import com.example.lulu.sygictravel.places.model.DetailedPlace
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlacesResponse(
	val places: List<ApiPlaceItemResponse>
) {
	fun fromApi(): List<DetailedPlace> {
		return places.map { it.fromApi() }
	}
}
