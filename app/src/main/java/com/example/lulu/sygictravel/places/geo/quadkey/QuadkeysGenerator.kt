package com.example.lulu.sygictravel.places.geo.quadkey

import com.example.lulu.sygictravel.places.model.geo.Bounds
import com.example.lulu.sygictravel.places.model.geo.Location
import java.util.ArrayList

/**
 *
 * Contains static methods for generating either one or more quadkeys.
 */
object QuadkeysGenerator {

	/**
	 *
	 * Generates quadkeys.
	 * @param bounds Map [bounding_box][Bounds], which the quadkeys are supposed to be generated for.
	 * *
	 * @param zoom Map zoom level.
	 * *
	 * @return List of generated quadkeys.
	 */
	fun generateQuadkeys(bounds: Bounds, zoom: Int): List<String> {
		val quadkeys = ArrayList<String>()
		val nw = getXYFromLatLng(bounds.north.toDouble(), bounds.west.toDouble(), zoom)
		val ne = getXYFromLatLng(bounds.north.toDouble(), bounds.east.toDouble(), zoom)
		val sw = getXYFromLatLng(bounds.south.toDouble(), bounds.west.toDouble(), zoom)

		val xMin = nw[0]
		val xMax = ne[0]
		val yMin = nw[1]
		val yMax = sw[1]

		for (x in xMin..xMax) {
			for (y in yMin..yMax) {
				quadkeys.add(toQuad(x, y, zoom))
			}
		}
		return quadkeys
	}

	/**
	 *
	 * Generates a single quadkey for given location and zoom.
	 * @param latLng Location, which the quadkey is supposed to be generated for.
	 * *
	 * @param zoom Map zoom level.
	 * *
	 * @return Generated quadkey.
	 */
	fun generateQuadkey(latLng: Location, zoom: Int): String {
		val xy = getXYFromLatLng(latLng.lat.toDouble(), latLng.lng.toDouble(), zoom)
		return toQuad(xy[0], xy[1], zoom)
	}

	/**
	 *
	 * Generates coordinates.
	 * @param lat Latitude.
	 * *
	 * @param lng Longitude.
	 * *
	 * @param zoom Zoom.
	 * *
	 * @return Generated coordinates.
	 */
	private fun getXYFromLatLng(lat: Double, lng: Double, zoom: Int): IntArray {
		val n = Math.pow(2.0, zoom.toDouble())
		val xtile = Math.floor(n * ((lng + 180) / 360)).toInt()
		val ytile = Math.floor(n * (1 - Math.log(Math.tan(lat * Math.PI / 180) + 1 / Math.cos(lat * Math.PI / 180)) / Math.PI) / 2).toInt()
		return intArrayOf(xtile, ytile)
	}

	/**
	 *
	 * Generates one quadkey from given coordinates and zoom.
	 * @param x X coordinate generated by [QuadkeysGenerator.getXYFromLatLng].
	 * *
	 * @param y Y coordinate generated by [QuadkeysGenerator.getXYFromLatLng].
	 * *
	 * @param z Zoom.
	 * *
	 * @return Generated quadkey.
	 */
	private fun toQuad(x: Int, y: Int, z: Int): String {
		var quadKey = ""
		for (i in z downTo 1) {
			val bitmask = 1 shl i - 1
			var digit = 0
			if (x and bitmask != 0) {
				digit = digit or 1
			}
			if (y and bitmask != 0) {
				digit = digit or 2
			}
			quadKey += digit
		}
		return quadKey
	}
}
