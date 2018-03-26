package com.example.lulu.sygictravel.places.model.geo

import java.io.Serializable

/**
 * Geographic location - latitude, longitude.
 */
data class Location(
	val lat: Float,
	val lng: Float
) : Serializable
