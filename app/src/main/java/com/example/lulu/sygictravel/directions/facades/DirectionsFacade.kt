package com.example.lulu.sygictravel.directions.facades

import com.example.lulu.sygictravel.directions.model.Directions
import com.example.lulu.sygictravel.directions.model.DirectionsRequest
import com.example.lulu.sygictravel.directions.services.DirectionsService
import com.example.lulu.sygictravel.utils.checkNotRunningOnMainThread

/**
 * Directions facade calculates estimated direction or calls the API for the real navigation polyline and time & distance calculations.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class DirectionsFacade internal constructor(
	private val directionsService: DirectionsService
) {
	/**
	 * Calculates directions fast & locally. Uses cached or air-distance estimated directions.
	 */
	fun getEstimatedDirections(requests: List<DirectionsRequest>): List<Directions> {
		checkNotRunningOnMainThread()
		return directionsService.getSimpleDirections(requests)
	}

	/**
	 * Queries the API for full directions. Reuses already cached directions.
	 */
	fun getComplexDirections(requests: List<DirectionsRequest>): List<Directions> {
		checkNotRunningOnMainThread()
		return directionsService.getComplexDirections(requests)
	}
}
