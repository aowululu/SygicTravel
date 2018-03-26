package com.example.lulu.sygictravel.places.api.model

import com.example.lulu.sygictravel.common.api.model.ApiLocationResponse
import com.example.lulu.sygictravel.places.api.TripCategoryConverter
import com.example.lulu.sygictravel.places.api.TripLevelConverter
import com.example.lulu.sygictravel.places.model.Place
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlaceListItemResponse(
	val id: String,
	val level: String,
	val categories: List<String>,
	val rating: Float,
	val quadkey: String,
	val location: ApiLocationResponse,
	val bounding_box: ApiBoundsResponse?,
	val name: String,
	val name_suffix: String?,
	val perex: String?,
	val url: String?,
	val thumbnail_url: String?,
	val marker: String,
	val parent_ids: List<String>,
	val star_rating: Float?,
	val star_rating_unofficial: Float?,
	val customer_rating: Float?,
	val owner_id: String?
) {
	fun fromApi(): Place {
		return Place(
			id = id,
			level = TripLevelConverter.fromApiLevel(level),
			categories = categories.mapNotNull { TripCategoryConverter.fromApiCategories(it) }.toSet(),
			rating = rating,
			quadkey = quadkey,
			location = location.fromApi(),
			boundingBox = bounding_box?.fromApi(),
			name = name,
			nameSuffix = name_suffix,
			perex = perex,
			url = url,
			thumbnailUrl = thumbnail_url,
			marker = marker,
			parentIds = parent_ids.toSet(),
			starRating = star_rating,
			starRatingUnofficial = star_rating_unofficial,
			customerRating = customer_rating,
			ownerId = owner_id
		)
	}
}
