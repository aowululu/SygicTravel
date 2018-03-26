package com.example.lulu.sygictravel.tours.api.model

import com.example.lulu.sygictravel.tours.model.Tour
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiTourResponse(
	val tours: List<ApiTourItemResponse>
) {
	fun fromApi(): List<Tour> {
		return tours.map { it.fromApi() }
	}
}
