package com.example.lulu.sygictravel.synchronization.services

import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.favorites.api.model.FavoriteRequest
import com.example.lulu.sygictravel.favorites.model.Favorite
import com.example.lulu.sygictravel.favorites.service.FavoriteService
import com.example.lulu.sygictravel.synchronization.model.SynchronizationResult

internal class FavoritesSynchronizationService constructor(
	private val apiClient: SygicTravelApiClient,
	private val favoriteService: FavoriteService
) {
	fun sync(addedFavoriteIds: List<String>, deletedFavoriteIds: List<String>, syncResult: SynchronizationResult) {
		for (favoriteId in addedFavoriteIds) {
			favoriteService.addPlace(favoriteId)
		}

		for (favoriteId in deletedFavoriteIds) {
			favoriteService.removePlace(favoriteId)
		}

		for (favorite in favoriteService.getFavoritesForSynchronization()) {
			if (favorite.state == Favorite.STATE_TO_ADD) {
				val response = apiClient.createFavorite(FavoriteRequest(favorite.id)).execute()
				if (response.isSuccessful) {
					favoriteService.markAsSynchronized(favorite)
				}
			} else if (favorite.state == Favorite.STATE_TO_REMOVE) {
				val response = apiClient.deleteFavorite(FavoriteRequest(favorite.id)).execute()
				if (response.isSuccessful) {
					favoriteService.markAsSynchronized(favorite)
				}
			}
		}

		syncResult.changedFavoriteIds.addAll(addedFavoriteIds.union(deletedFavoriteIds))
	}
}
