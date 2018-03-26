package com.example.lulu.sygictravel.common.database

import android.arch.persistence.room.TypeConverter
import com.example.lulu.sygictravel.directions.model.DirectionAvoid
import com.example.lulu.sygictravel.directions.model.DirectionMode
import com.example.lulu.sygictravel.places.model.geo.Location
import com.example.lulu.sygictravel.trips.model.TripItemTransportWaypoint
import com.example.lulu.sygictravel.trips.model.TripPrivacyLevel

internal class Converters {
	@TypeConverter
	fun stringListToString(list: ArrayList<String>?): String? {
		return when (list) {
			null -> null
			else -> list.joinToString(",")
		}
	}

	@TypeConverter
	fun stringToStringList(string: String?): ArrayList<String>? {
		return when (string) {
			null -> null
			else -> ArrayList(string.split(","))
		}
	}

	@TypeConverter
	fun privacyLevelToString(level: TripPrivacyLevel?): String? {
		return when (level) {
			null -> null
			else -> level.name
		}
	}

	@TypeConverter
	fun stringToPrivacyLevel(level: String?): TripPrivacyLevel? {
		return when (level) {
			null -> null
			else -> TripPrivacyLevel.valueOf(level)
		}
	}

	@TypeConverter
	fun modeToString(mode: DirectionMode?): String? {
		return when (mode) {
			null -> null
			else -> mode.name
		}
	}

	@TypeConverter
	fun stringToMode(mode: String?): DirectionMode? {
		return when (mode) {
			null -> null
			else -> DirectionMode.valueOf(mode)
		}
	}

	@TypeConverter
	fun avoidListToString(avoidList: ArrayList<DirectionAvoid>?): String? {
		return when (avoidList) {
			null -> ""
			else -> avoidList.joinToString(",") { it.name }
		}
	}

	@TypeConverter
	fun stringToAvoidList(avoidString: String?): ArrayList<DirectionAvoid>? {
		return when (avoidString) {
			null, "" -> arrayListOf()
			else -> ArrayList(avoidString.split(",").map { DirectionAvoid.valueOf(it) })
		}
	}

	@TypeConverter
	fun tripItemTransportWaypointToString(avoidList: ArrayList<TripItemTransportWaypoint>?): String? {
		return when (avoidList) {
			null -> ""
			else -> avoidList.joinToString(",") { it.placeId + ';' + it.location.lat + ';' + it.location.lng }
		}
	}

	@TypeConverter
	fun stringToTripItemTransportWaypoint(avoidString: String?): ArrayList<TripItemTransportWaypoint>? {
		return when (avoidString) {
			null, "" -> arrayListOf()
			else -> ArrayList(avoidString.split(",").map {
				val parts = it.split(";")
				TripItemTransportWaypoint(
					placeId = when (parts[0].isEmpty()) {
						true -> null
						false -> parts[0]
					},
					location = Location(
						lat = parts[1].toFloat(),
						lng = parts[2].toFloat()
					)
				)
			})
		}
	}
}
