package com.example.lulu.sygictravel.directions.model

import java.io.Serializable

class Direction(
	val mode: DirectionMode,
	/** Duration in seconds */
	val duration: Int?,
	/** Distance in meters */
	val distance: Int?,
	val polyline: String?,
	val isEstimated: Boolean
) : Serializable
