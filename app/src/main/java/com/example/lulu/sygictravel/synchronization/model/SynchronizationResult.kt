package com.example.lulu.sygictravel.synchronization.model

class SynchronizationResult(
	val changedTripIds: MutableSet<String> = mutableSetOf(),
	val changedFavoriteIds: MutableSet<String> = mutableSetOf(),
	val cratedTripIdsMapping: MutableMap<String, String> = mutableMapOf(),
	var success: Boolean = true,
	var exception: Exception? = null
)
