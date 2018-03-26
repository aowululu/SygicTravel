package com.example.lulu.sygictravel.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

internal object DateTimeHelper {
	private val timezone = TimeZone.getTimeZone("UTC")
	private val outDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
	private val outDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
	private val parseDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
	private val parseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

	init {
		outDateTimeFormat.timeZone = timezone
		outDateFormat.timeZone = timezone
		parseDateTimeFormat.timeZone = timezone
		parseDateFormat.timeZone = timezone
	}

	fun timestampToDatetime(timestamp: Long?): String? {
		return when (timestamp) {
			null -> null
			else -> outDateTimeFormat.format(Date(timestamp * 1000))
		}
	}

	fun timestampToDate(timestamp: Long?): String? {
		return when (timestamp) {
			null -> null
			else -> outDateFormat.format(Date(timestamp * 1000))
		}
	}

	fun datetimeToTimestamp(datetime: String?): Long? {
		return when (datetime) {
			null -> null
			else -> {
				var string = datetime
				string = string.replace("Z", "+00:00")
				if (string.getOrNull(22) == ':') {
					// strip : in timezone part
					string = string.substring(0, 22) + string.substring(23)
				}
				val parsed = parseDateTimeFormat.parse(string)
				return parsed.time / 1000
			}
		}
	}

	fun dateToTimestamp(date: String?): Long? {
		return when (date) {
			null -> null
			else -> {
				val parsed = parseDateFormat.parse(date)
				return parsed.time / 1000L
			}
		}
	}

	fun now(): Date {
		return Date()
	}
}
