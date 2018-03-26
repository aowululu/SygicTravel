package com.example.lulu.sygictravel.places.api.model

import com.example.lulu.sygictravel.places.model.DetailedPlace
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlaceResponse(
	val place: ApiPlaceItemResponse
) {
	fun fromApi(): DetailedPlace {
		return place.fromApi()
	}
}
