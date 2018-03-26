package com.example.lulu.sygictravel.places.api.model

import com.example.lulu.sygictravel.common.api.model.ApiLocationResponse
import com.example.lulu.sygictravel.places.api.TripCategoryConverter
import com.example.lulu.sygictravel.places.api.TripLevelConverter
import com.example.lulu.sygictravel.places.model.Description
import com.example.lulu.sygictravel.places.model.DescriptionProvider
import com.example.lulu.sygictravel.places.model.Detail
import com.example.lulu.sygictravel.places.model.DetailedPlace
import com.example.lulu.sygictravel.places.model.Reference
import com.example.lulu.sygictravel.places.model.Tag
import com.example.lulu.sygictravel.places.model.TranslationProvider
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlaceItemResponse(
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
	val owner_id: String?,
	val tags: List<ApiTag>,
	val description: ApiDescription?,
	val address: String?,
	val admission: String?,
	val duration: Int?,
	val email: String?,
	val opening_hours: String?,
	val phone: String?,
	val main_media: ApiMainMediaResponse?,
	val references: List<ApiReference>
) {
	class ApiDescription(
		val text: String,
		val provider: String?,
		val translation_provider: String?,
		val link: String?
	) {
		companion object {
			private const val PROVIDER_WIKIPEDIA = "wikipedia"
			private const val PROVIDER_WIKIVOYAGE = "wikivoyage"
			private const val TRANSLATION_PROVIDER_GOOGLE = "google"
		}

		fun fromApi(): Description {
			return Description(
				text = text,
				provider = when (provider) {
					PROVIDER_WIKIPEDIA -> DescriptionProvider.WIKIPEDIA
					PROVIDER_WIKIVOYAGE -> DescriptionProvider.WIKIVOYAGE
					else -> DescriptionProvider.NONE
				},
				translationProvider = when (translation_provider) {
					TRANSLATION_PROVIDER_GOOGLE -> TranslationProvider.GOOGLE
					else -> TranslationProvider.NONE
				},
				link = link
			)
		}
	}

	class ApiTag(
		val key: String,
		val name: String
	) {
		fun fromApi(): Tag {
			return Tag(
				key = key,
				name = name
			)
		}
	}

	class ApiReference(
		val id: Int,
		val title: String,
		val type: String,
		val language_id: String?,
		val url: String,
		val supplier: String?,
		val priority: Int,
		val currency: String?,
		val price: Float?,
		val flags: List<String>
	) {
		fun fromApi(): Reference {
			val reference = Reference()
			reference.id = id
			reference.title = title
			reference.type = type
			reference.languageId = language_id
			reference.url = url
			reference.supplier = supplier
			reference.priority = priority
			reference.currency = currency
			reference.price = price
			reference.flags = flags
			return reference
		}
	}

	fun fromApi(): DetailedPlace {
		val detail = Detail(
			tags = tags.map { it.fromApi() },
			description = description?.fromApi(),
			address = address,
			admission = admission,
			duration = duration,
			email = email,
			openingHours = opening_hours,
			phone = phone,
			mainMedia = main_media?.media?.map {
				it.fromApi()
			} ?: emptyList(),
			references = references.map { it.fromApi() }
		)

		return DetailedPlace(
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
			detail = detail,
			starRating = star_rating,
			starRatingUnofficial = star_rating_unofficial,
			customerRating = customer_rating,
			ownerId = owner_id
		)
	}
}
