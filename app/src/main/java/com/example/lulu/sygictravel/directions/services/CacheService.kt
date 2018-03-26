package com.example.lulu.sygictravel.directions.services

import android.content.Context
import com.example.lulu.sygictravel.directions.helpers.CachingHelper
import com.example.lulu.sygictravel.directions.model.Directions
import com.example.lulu.sygictravel.directions.model.DirectionsRequest
import java.io.File

internal class CacheService constructor(
	context: Context
) {
	private val cache = CachingHelper<Directions>(File(context.cacheDir.path + File.separator + "sygic-travel-directions"))

	fun getCachedDirections(requests: List<DirectionsRequest>): List<Directions?> {
		return requests.map { getCachedDirections(it) }
	}

	fun storeDirections(requests: List<DirectionsRequest>, allDirections: List<Directions?>) {
		allDirections.forEachIndexed { i, directions ->
			if (directions != null) {
				val key = getCacheKey(requests[i])
				cache.put(key, directions)
			}
		}
	}

	private fun getCachedDirections(request: DirectionsRequest): Directions? {
		return cache.get(getCacheKey(request))
	}

	private fun getCacheKey(request: DirectionsRequest): String {
		return request.hashCode().toString()
	}
}
