package com.example.lulu.sygictravel.common.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.lulu.sygictravel.favorites.model.Favorite
import com.example.lulu.sygictravel.favorites.model.daos.FavoriteDao
import com.example.lulu.sygictravel.trips.database.daos.TripDayItemsDao
import com.example.lulu.sygictravel.trips.database.daos.TripDaysDao
import com.example.lulu.sygictravel.trips.database.daos.TripsDao
import com.example.lulu.sygictravel.trips.database.entities.Trip
import com.example.lulu.sygictravel.trips.database.entities.TripDay
import com.example.lulu.sygictravel.trips.database.entities.TripDayItem

@Database(
	entities = [
		Favorite::class,
		Trip::class,
		TripDay::class,
		TripDayItem::class
	],
	version = 2
)
@TypeConverters(Converters::class)
abstract internal class Database : RoomDatabase() {
	abstract fun favoriteDao(): FavoriteDao
	abstract fun tripsDao(): TripsDao
	abstract fun tripDaysDao(): TripDaysDao
	abstract fun tripDayItemsDao(): TripDayItemsDao
}
