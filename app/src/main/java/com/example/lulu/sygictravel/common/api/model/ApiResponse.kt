package com.example.lulu.sygictravel.common.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiResponse<out T>(
	val status_code: Int,
	val server_timestamp: String,
	val data: T?
)
