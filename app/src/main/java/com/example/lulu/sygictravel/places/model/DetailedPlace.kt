package com.example.lulu.sygictravel.places.model

import com.example.lulu.sygictravel.places.model.geo.Bounds
import com.example.lulu.sygictravel.places.model.geo.Location

class DetailedPlace(
	id: String,
	level: Level,
	categories: Set<Category>,
	rating: Float,
	quadkey: String,
	location: Location,
	name: String,
	nameSuffix: String?,
	boundingBox: Bounds?,
	perex: String?,
	url: String?,
	thumbnailUrl: String?,
	marker: String,
	parentIds: Set<String>,
	starRating: Float?,
	starRatingUnofficial: Float?,
	customerRating: Float?,
	ownerId: String?,
	val detail: Detail
) : Place(
	id,
	level,
	categories,
	rating,
	quadkey,
	location,
	name,
	nameSuffix,
	boundingBox,
	perex,
	url,
	thumbnailUrl,
	marker,
	parentIds,
	starRating,
	starRatingUnofficial,
	customerRating,
	ownerId
)
