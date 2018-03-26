package com.example.lulu.sygictravel.places.api.model

import com.example.lulu.sygictravel.places.model.geo.Bounds
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiBoundsResponse(
	val north: Float,
	val east: Float,
	val south: Float,
	val west: Float
) {
	fun fromApi(): Bounds {
		return Bounds(
			north = north,
			east = east,
			south = south,
			west = west
		)
	}
}
