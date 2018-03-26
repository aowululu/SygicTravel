package com.example.lulu.sygictravel.trips.api

import com.example.lulu.sygictravel.trips.api.model.ApiTripItemRequest
import com.example.lulu.sygictravel.trips.api.model.ApiTripItemResponse
import com.example.lulu.sygictravel.trips.api.model.ApiTripListItemResponse
import com.example.lulu.sygictravel.trips.model.Trip
import com.example.lulu.sygictravel.trips.model.TripInfo
import com.example.lulu.sygictravel.trips.model.TripMedia
import com.example.lulu.sygictravel.trips.model.TripPrivacyLevel
import com.example.lulu.sygictravel.trips.model.TripPrivileges
import com.example.lulu.sygictravel.utils.DateTimeHelper
import com.example.lulu.sygictravel.utils.asDate
import com.example.lulu.sygictravel.utils.timeSeconds

@Suppress("DEPRECATION")
internal class TripConverter constructor(
	private val tripDayConverter: TripDayConverter
) {
	fun fromApi(apiTrip: ApiTripListItemResponse): TripInfo {
		val localTrip = Trip(apiTrip.id)
		localTrip.ownerId = apiTrip.owner_id
		localTrip.name = apiTrip.name
		localTrip.version = apiTrip.version
		localTrip.url = apiTrip.url
		localTrip.updatedAt = DateTimeHelper.datetimeToTimestamp(apiTrip.updated_at)!!.asDate()
		localTrip.isDeleted = apiTrip.is_deleted
		localTrip.privacyLevel = when (apiTrip.privacy_level) {
			ApiTripListItemResponse.PRIVACY_PUBLIC -> TripPrivacyLevel.PUBLIC
			ApiTripListItemResponse.PRIVACY_PRIVATE -> TripPrivacyLevel.PRIVATE
			ApiTripListItemResponse.PRIVACY_SHAREABLE -> TripPrivacyLevel.SHAREABLE
			else -> TripPrivacyLevel.PRIVATE
		}
		localTrip.startsOn = DateTimeHelper.dateToTimestamp(apiTrip.starts_on)?.asDate()
		localTrip.daysCount = apiTrip.days_count
		localTrip.media = if (apiTrip.media != null) TripMedia(
			squareMediaId = apiTrip.media.square.id,
			squareUrlTemplate = apiTrip.media.square.url_template,
			landscapeMediaId = apiTrip.media.landscape.id,
			landscapeUrlTemplate = apiTrip.media.landscape.url_template,
			portraitId = apiTrip.media.portrait.id,
			portraitUrlTemplate = apiTrip.media.portrait.url_template,
			videoPreviewId = apiTrip.media.video_preview?.id,
			videoPreviewUrlTemplate = apiTrip.media.video_preview?.url_template
		) else null

		localTrip.privileges = TripPrivileges(
			edit = apiTrip.privileges.edit,
			manage = apiTrip.privileges.manage,
			delete = apiTrip.privileges.delete
		)
		return localTrip
	}

	fun fromApi(apiTrip: ApiTripItemResponse): Trip {
		val localTrip = fromApi(apiTrip as ApiTripListItemResponse) as Trip
		localTrip.destinations = ArrayList(apiTrip.destinations)
		localTrip.days = apiTrip.days.map { tripDayConverter.fromApi(it, localTrip) }
		return localTrip
	}

	fun toApi(localTrip: Trip): ApiTripItemRequest {
		return ApiTripItemRequest(
			name = localTrip.name,
			base_version = localTrip.version,
			updated_at = DateTimeHelper.timestampToDatetime(localTrip.updatedAt!!.timeSeconds)!!,
			is_deleted = localTrip.isDeleted,
			privacy_level = when (localTrip.privacyLevel) {
				TripPrivacyLevel.PUBLIC -> ApiTripListItemResponse.PRIVACY_PUBLIC
				TripPrivacyLevel.PRIVATE -> ApiTripListItemResponse.PRIVACY_PRIVATE
				TripPrivacyLevel.SHAREABLE -> ApiTripListItemResponse.PRIVACY_SHAREABLE
			},
			starts_on = DateTimeHelper.timestampToDate(localTrip.startsOn?.timeSeconds),
			destinations = localTrip.destinations,
			days = localTrip.days.map { tripDayConverter.toApi(it) }
		)
	}
}
