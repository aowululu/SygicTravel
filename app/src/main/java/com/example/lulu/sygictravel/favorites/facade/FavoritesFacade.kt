package com.example.lulu.sygictravel.favorites.facade

import com.example.lulu.sygictravel.favorites.service.FavoriteService
import com.example.lulu.sygictravel.utils.checkNotRunningOnMainThread

/**
 * Favorites facade provides methods for managing users' favorite places.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class FavoritesFacade internal constructor(
	private val favoritesService: FavoriteService
) {
	fun addToFavorites(placeId: String) {
		checkNotRunningOnMainThread()
		return favoritesService.addPlace(placeId)
	}

	fun removeFromFavorites(placeId: String) {
		checkNotRunningOnMainThread()
		return favoritesService.removePlace(placeId)
	}

	fun getFavoritePlaceIds(): List<String> {
		checkNotRunningOnMainThread()
		return favoritesService.getPlaces()
	}

	internal fun clearUserData() {
		favoritesService.clearUserData()
	}
}
