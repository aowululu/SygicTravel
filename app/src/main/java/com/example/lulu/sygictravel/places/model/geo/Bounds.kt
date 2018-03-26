package com.example.lulu.sygictravel.places.model.geo

/**
 * Geographical bounding_box - south, west, north, east.
 */
data class Bounds constructor(
	var north: Float,
	var east: Float,
	var south: Float,
	var west: Float
) {
	internal fun toApiQuery(): String {
		return south.toString() + "," + west + "," + north + "," + east
	}
}
