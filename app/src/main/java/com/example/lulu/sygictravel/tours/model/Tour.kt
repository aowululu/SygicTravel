package com.example.lulu.sygictravel.tours.model

/**
 * Model class for Tour.
 */
class Tour {
	var id: String? = null
	var supplier: String? = null
	var title: String? = null
	var perex: String? = null
	var url: String? = null
	var rating: Float? = null
	var reviewCount: Int? = null
	var photoUrl: String? = null
	var price: Float? = null
	var originalPrice: Float? = null
	var duration: String? = null
	var durationMin: Int? = null
	var durationMax: Int? = null
	var flags: List<String>? = null
}
