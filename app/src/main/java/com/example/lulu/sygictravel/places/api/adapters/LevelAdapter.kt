package com.example.lulu.sygictravel.places.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.lulu.sygictravel.places.model.Level

class LevelAdapter {
	@ToJson
	fun toJson(level: Level): String {
		return level.name.toLowerCase()
	}

	@FromJson
	fun fromJson(level: String): Level {
		return Level.valueOf(level.toUpperCase())
	}
}
