package com.example.lulu.sygictravel.directions.services

import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.common.api.model.ApiResponse
import com.example.lulu.sygictravel.directions.api.model.ApiDirectionRequest
import com.example.lulu.sygictravel.directions.api.model.ApiDirectionsResponse
import com.example.lulu.sygictravel.directions.helpers.AirDistanceCalculator
import com.example.lulu.sygictravel.directions.model.Direction
import com.example.lulu.sygictravel.directions.model.DirectionAvoid
import com.example.lulu.sygictravel.directions.model.DirectionMode
import com.example.lulu.sygictravel.directions.model.Directions
import com.example.lulu.sygictravel.directions.model.DirectionsRequest

internal class ApiDirectionsService constructor(
	private val apiClient: SygicTravelApiClient,
	private val naiveDirectionsService: NaiveDirectionsService
) {
	fun getDirections(requests: List<DirectionsRequest>): List<Directions?> {
		val directions = getCalculatedDirections(requests)
		return directions.mapIndexed { i, it ->
			if (it == null || it.isEmpty()) {
				return@mapIndexed null
			}

			val request = requests[i]
			val airDistance = AirDistanceCalculator.getAirDistance(request.startLocation, request.endLocation)
			val pedestrian = arrayListOf<Direction>()
			val car = arrayListOf<Direction>()
			val plane = arrayListOf<Direction>()

			if (airDistance <= DirectionsService.PEDESTRIAN_MAX_LIMIT) {
				it.filterTo(pedestrian, { it2 -> it2.mode == DirectionMode.PEDESTRIAN })
			}
			if (airDistance <= DirectionsService.CAR_MAX_LIMIT) {
				it.filterTo(car, { it2 -> it2.mode == DirectionMode.CAR })
			}
			if (airDistance > DirectionsService.PLANE_MIN_LIMIT) {
				plane.add(naiveDirectionsService.getPlaneDirection(airDistance))
			}

			if (pedestrian.isEmpty() && car.isEmpty()) {
				return@mapIndexed null
			} else {
				return@mapIndexed Directions(
					startLocation = request.startLocation,
					endLocation = request.endLocation,
					waypoints = request.waypoints,
					avoid = request.avoid,
					airDistance = airDistance,
					pedestrian = pedestrian,
					car = car,
					plane = plane
				)
			}
		}
	}

	private fun getCalculatedDirections(requests: List<DirectionsRequest>): List<List<Direction>?> {
		val apiRequests = requests.map {
			ApiDirectionRequest(
				origin = ApiDirectionRequest.Location(it.startLocation.lat, it.startLocation.lng),
				destination = ApiDirectionRequest.Location(it.endLocation.lat, it.endLocation.lng),
				avoid = it.avoid.map {
					when (it) {
						DirectionAvoid.TOLLS -> ApiDirectionRequest.AVOID_TOLLS
						DirectionAvoid.HIGHWAYS -> ApiDirectionRequest.AVOID_HIGHWAYS
						DirectionAvoid.FERRIES -> ApiDirectionRequest.AVOID_FERRIES
						DirectionAvoid.UNPAVED -> ApiDirectionRequest.AVOID_UNPAVED
					}
				},
				waypoints = it.waypoints.map {
					ApiDirectionRequest.Location(it.lat, it.lng)
				}
			)
		}

		val response: ApiResponse<ApiDirectionsResponse>
		try {
			response = apiClient.getDirections(apiRequests).execute().body()!!
		} catch (_: Exception) {
			return (0 until requests.size).map { null }
		}

		val directions = response.data!!.path.map { it.directions }
		assert(directions.size == requests.size)

		return directions.map {
			it.filter { it.enumMode != null }.map {
				Direction(
					mode = it.enumMode!!,
					duration = it.duration,
					distance = it.distance,
					polyline = it.polyline,
					isEstimated = false
				)
			}
		}
	}
}
