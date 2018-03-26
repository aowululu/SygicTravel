package com.example.lulu.sygictravel.places.facade

import com.example.lulu.sygictravel.places.model.DetailedPlace
import com.example.lulu.sygictravel.places.model.Place
import com.example.lulu.sygictravel.places.model.media.Medium
import com.example.lulu.sygictravel.places.service.PlacesService
import com.example.lulu.sygictravel.utils.checkNotRunningOnMainThread

/**
 * Places facade provides interface for fetching places data from the API.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class PlacesFacade internal constructor(
	private val placesService: PlacesService
) {
	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param placesQuery PlacesQuery encapsulating data for API request.
	 */
	fun getPlaces(placesQuery: PlacesQuery): List<Place> {
		checkNotRunningOnMainThread()
		return placesService.getPlaces(placesQuery)
	}

	/**
	 * Creates and sends a request to get place with detailed information.
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 */
	fun getPlaceDetailed(id: String): DetailedPlace {
		checkNotRunningOnMainThread()
		return placesService.getPlaceDetailed(id)
	}

	/**
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 */
	fun getPlacesDetailed(ids: List<String>): List<DetailedPlace> {
		checkNotRunningOnMainThread()
		return placesService.getPlacesDetailed(ids)
	}

	/**
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 */
	fun getPlaceMedia(id: String): List<Medium> {
		checkNotRunningOnMainThread()
		return placesService.getPlaceMedia(id)
	}
}
