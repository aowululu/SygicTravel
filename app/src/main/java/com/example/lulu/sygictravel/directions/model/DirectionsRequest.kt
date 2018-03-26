package com.example.lulu.sygictravel.directions.model

import com.example.lulu.sygictravel.places.model.geo.Location

data class DirectionsRequest(
	val startLocation: Location,
	val endLocation: Location,
	val waypoints: List<Location>,
	val avoid: List<DirectionAvoid>
)
