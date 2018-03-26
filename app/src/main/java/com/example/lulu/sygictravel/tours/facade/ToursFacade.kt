package com.example.lulu.sygictravel.tours.facade

import com.example.lulu.sygictravel.tours.model.Tour
import com.example.lulu.sygictravel.tours.service.ToursService
import com.example.lulu.sygictravel.utils.checkNotRunningOnMainThread

/**
 * Tours facade provides interface for accessing tours from Viator and GetYourGuide providers through Sygic Travel API.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class ToursFacade internal constructor(
	private val toursService: ToursService
) {
	/**
	 * Creates and sends a request to get the Viator Tours.
	 */
	fun getToursViator(query: ToursViatorQuery): List<Tour> {
		checkNotRunningOnMainThread()
		return toursService.getToursViator(query)
	}

	/**
	 * Creates and sends a request to get the Get Your Guide Tours.
	 */
	fun getToursGetYourGuide(query: ToursGetYourGuideQuery): List<Tour> {
		checkNotRunningOnMainThread()
		return toursService.getToursGetYourGuide(query)
	}
}
