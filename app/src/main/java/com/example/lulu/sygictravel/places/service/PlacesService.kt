package com.example.lulu.sygictravel.places.service

import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.places.facade.PlacesQuery
import com.example.lulu.sygictravel.places.model.DetailedPlace
import com.example.lulu.sygictravel.places.model.Place
import com.example.lulu.sygictravel.places.model.media.Medium

internal class PlacesService(
	private val sygicTravelApiClient: SygicTravelApiClient
) {
	fun getPlaces(
		placesQuery: PlacesQuery
	): List<Place> {
		val request = sygicTravelApiClient.getPlaces(
			query = placesQuery.query,
			levels = placesQuery.getLevelsApiQuery(),
			categories = placesQuery.getCategoriesApiQuery(),
			mapTiles = placesQuery.getMapTilesApiQuery(),
			mapSpread = placesQuery.mapSpread,
			bounds = placesQuery.bounds?.toApiQuery(),
			tags = placesQuery.getTagsApiQuery(),
			parents = placesQuery.getParentsApiQuery(),
			limit = placesQuery.limit
		)
		val response = request.execute()
		return response.body()!!.data!!.fromApi()
	}

	fun getPlaceDetailed(id: String): DetailedPlace {
		val request = sygicTravelApiClient.getPlaceDetailed(id)
		return request.execute().body()!!.data!!.fromApi()
	}

	fun getPlacesDetailed(ids: List<String>): List<DetailedPlace> {
		val queryIds = ids.joinToString(PlacesQuery.LogicOperator.OR.apiOperator)
		val request = sygicTravelApiClient.getPlacesDetailed(queryIds)
		return request.execute().body()!!.data!!.fromApi()
	}

	fun getPlaceMedia(id: String): List<Medium> {
		val request = sygicTravelApiClient.getPlaceMedia(id)
		return request.execute().body()!!.data!!.fromApi()
	}
}
