package com.example.lulu.sygictravel.favorites.service

import com.example.lulu.sygictravel.favorites.model.Favorite
import com.example.lulu.sygictravel.favorites.model.daos.FavoriteDao

internal class FavoriteService(
	private val favoriteDao: FavoriteDao
) {
	fun addPlace(id: String) {
		val favorite = Favorite()
		favorite.id = id
		favorite.state = Favorite.STATE_TO_ADD
		favoriteDao.insert(favorite)
	}

	fun removePlace(id: String) {
		val favorite = favoriteDao.get(id) ?: return
		favorite.state = Favorite.STATE_TO_REMOVE
		favoriteDao.insert(favorite)
	}

	fun getPlaces(): List<String> {
		val favorites = favoriteDao.findAll()
		return favorites.map { it.id }
	}

	fun getFavoritesForSynchronization(): List<Favorite> {
		return favoriteDao.findForSynchronization()
	}

	fun hasChangesToSynchronize(): Boolean {
		return favoriteDao.getAllChangedCount() > 0
	}

	fun markAsSynchronized(favorite: Favorite) {
		favorite.state = Favorite.STATE_SYNCED
		favoriteDao.update(favorite)
	}

	fun clearUserData() {
		favoriteDao.deleteAll()
	}
}
