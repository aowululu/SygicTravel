package com.example.lulu.sygictravel.places.model

import com.example.lulu.sygictravel.places.model.geo.Bounds
import com.example.lulu.sygictravel.places.model.geo.Location

open class Place(
	val id: String,
	val level: Level,
	val categories: Set<Category>,
	val rating: Float,
	val quadkey: String,
	val location: Location,
	val name: String,
	val nameSuffix: String?,
	val boundingBox: Bounds?,
	val perex: String?,
	val url: String?,
	val thumbnailUrl: String?,
	val marker: String,
	val parentIds: Set<String>,
	val starRating: Float?,
	val starRatingUnofficial: Float?,
	val customerRating: Float?,
	val ownerId: String?
)
