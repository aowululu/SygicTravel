package com.example.lulu.sygictravel.favorites.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class FavoriteRequest(
	val place_id: String
)
