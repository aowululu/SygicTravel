package com.example.lulu.sygictravel.common.api.model

import com.example.lulu.sygictravel.places.model.geo.Location

internal class ApiLocationResponse(
	val lat: Float,
	val lng: Float
) {
	fun fromApi(): Location {
		return Location(lat, lng)
	}
}
