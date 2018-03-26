package com.example.lulu.sygictravel.places.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiMainMediaResponse(
	val media: List<ApiMediumResponse>
)
