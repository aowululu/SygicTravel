package com.example.lulu.sygictravel.favorites.model.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.example.lulu.sygictravel.favorites.model.Favorite

/**
 * Interface containing methods for working with favorite table in database.
 */
@Dao
internal interface FavoriteDao {
	/**
	 * @return All favorite places' ids.
	 */
	@Query("SELECT * FROM favorites WHERE state < 2")
	fun findAll(): List<Favorite>

	@Query("SELECT * FROM favorites WHERE state > 0")
	fun findForSynchronization(): List<Favorite>

	@Query("SELECT COUNT(*) FROM favorites WHERE state > 0")
	fun getAllChangedCount(): Int

	@Query("SELECT * FROM favorites WHERE id = :id")
	fun get(id: String): Favorite?

	/**
	 * Inserts one place's id into favorite table. If the id has already been inserted before, the
	 * insertion of [placeId] is skipped. No error is provided.
	 * @param placeId An id to be stored.
	 * @return A rowid of the inserted place id (row).
	 */
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(placeId: Favorite): Long

	@Update
	fun update(favorite: Favorite)

	/**
	 * Removes a given place id from the favorite table.
	 * @param placeId An id to be removed.
	 * @return A number of deleted rows.
	 */
	@Delete
	fun delete(placeId: Favorite): Int

	@Query("DELETE FROM favorites")
	fun deleteAll()
}
