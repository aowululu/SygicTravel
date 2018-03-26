package com.example.lulu.sygictravel.session.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ResetPasswordRequest(
	val email: String
)
