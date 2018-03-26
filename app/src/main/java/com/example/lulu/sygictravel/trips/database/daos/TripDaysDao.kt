package com.example.lulu.sygictravel.trips.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.lulu.sygictravel.trips.database.entities.TripDay

@Dao
internal interface TripDaysDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun replaceAll(vararg tripDay: TripDay)

	@Query("DELETE FROM trip_days WHERE trip_id = :tripId AND day_index > :dayIndex")
	fun removeOverDayIndex(tripId: String, dayIndex: Int)

	@Query("SELECT * FROM trip_days WHERE trip_id = :tripId ORDER BY day_index")
	fun findByTripId(tripId: String): List<TripDay>

	@Query("SELECT * FROM trip_days WHERE trip_id IN (:tripIds) ORDER BY trip_id, day_index")
	fun findByTripId(tripIds: List<String>): List<TripDay>

	@Query("DELETE FROM trip_days WHERE trip_id = :tripId")
	fun deleteByTrip(tripId: String)

	@Query("DELETE FROM trip_days")
	fun deleteAll()
}
