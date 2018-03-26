package com.example.lulu.sygictravel.tours.service

import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.tours.facade.ToursGetYourGuideQuery
import com.example.lulu.sygictravel.tours.facade.ToursViatorQuery
import com.example.lulu.sygictravel.tours.model.Tour
import com.example.lulu.sygictravel.utils.DateTimeHelper
import com.example.lulu.sygictravel.utils.timeSeconds

internal class ToursService(
	private val apiClient: SygicTravelApiClient
) {
	fun getToursViator(query: ToursViatorQuery): List<Tour> {
		val request = apiClient.getToursViator(
			parentPlaceId = query.parentPlaceId,
			page = query.page,
			sortBy = query.sortBy?.apiSortBy,
			sortDirection = query.sortDirection?.apiSortDirection
		)
		return request.execute().body()!!.data!!.fromApi()
	}

	fun getToursGetYourGuide(query: ToursGetYourGuideQuery): List<Tour> {
		val request = apiClient.getToursGetYourGuide(
			query = query.query,
			bounds = query.bounds?.toApiQuery(),
			parentPlaceId = query.parentPlaceId,
			tags = query.tags,
			from = DateTimeHelper.timestampToDatetime(query.startDate?.timeSeconds),
			to = DateTimeHelper.timestampToDatetime(query.endDate?.timeSeconds),
			duration = query.getApiDurationQuery(),
			page = query.page,
			count = query.count,
			sortBy = query.sortBy?.apiSortBy,
			sortDirection = query.sortDirection?.apiSortDirection
		)
		return request.execute().body()!!.data!!.fromApi()
	}
}
