package com.example.lulu.sygictravel.places.api.model

import com.example.lulu.sygictravel.common.api.model.ApiLocationResponse
import com.example.lulu.sygictravel.places.model.media.Attribution
import com.example.lulu.sygictravel.places.model.media.Medium
import com.example.lulu.sygictravel.places.model.media.Original
import com.example.lulu.sygictravel.places.model.media.Suitability
import com.example.lulu.sygictravel.places.model.media.Type
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiMediumResponse(
	val id: String,
	val type: String,
	val url_template: String,
	val url: String,
	val original: ApiOriginal?,
	val suitability: List<String>,
	val attribution: ApiAttribution,
	val location: ApiLocationResponse?
) {
	companion object {
		private const val TYPE_PHOTO = "photo"
		private const val TYPE_PHOTO_360 = "photo360"
		private const val TYPE_VIDEO = "video"
		private const val TYPE_VIDEO_360 = "video360"
		private const val SUITABILITY_PORTRAIT = "portrait"
		private const val SUITABILITY_LANDSCAPE = "landscape"
		private const val SUITABILITY_SQUARE = "square"
		private const val SUITABILITY_VIDEO_PREVIEW = "video_preview"
	}

	class ApiOriginal(
		val size: Int?,
		val width: Int?,
		val height: Int?
	) {
		fun fromApi(): Original {
			return Original(
				size = size,
				width = width,
				height = height
			)
		}
	}

	class ApiAttribution(
		val author: String?,
		val author_url: String?,
		val license: String?,
		val license_url: String?,
		val other: String?,
		val title: String?,
		val title_url: String?
	) {
		fun fromApi(): Attribution {
			return Attribution(
				author = author,
				authorUrl = author_url,
				license = license,
				licenseUrl = license_url,
				other = other,
				title = title,
				titleUrl = title_url
			)
		}
	}

	fun fromApi(): Medium {
		return Medium(
			id = id,
			type = when (type) {
				TYPE_PHOTO -> Type.PHOTO
				TYPE_PHOTO_360 -> Type.PHOTO_360
				TYPE_VIDEO -> Type.VIDEO
				TYPE_VIDEO_360 -> Type.VIDEO_360
				else -> Type.PHOTO
			},
			urlTemplate = url_template,
			url = url,
			original = original?.fromApi(),
			suitability = suitability.mapNotNull {
				when (it) {
					SUITABILITY_LANDSCAPE -> Suitability.LANDSCAPE
					SUITABILITY_PORTRAIT -> Suitability.PORTRAIT
					SUITABILITY_SQUARE -> Suitability.SQUARE
					SUITABILITY_VIDEO_PREVIEW -> Suitability.VIDEO_PREVIEW
					else -> null
				}
			}.toSet(),
			attribution = attribution.fromApi(),
			location = location?.fromApi()
		)
	}
}
