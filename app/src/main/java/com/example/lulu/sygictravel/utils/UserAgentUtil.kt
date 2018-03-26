package com.example.lulu.sygictravel.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.example.lulu.sygictravel.BuildConfig

internal object UserAgentUtil {
	fun createUserAgent(context: Context): String {
		val packageVersion: String = try {
			context.packageManager.getPackageInfo(context.packageName, 0).versionName
		} catch (e: PackageManager.NameNotFoundException) {
			"unknown"
		}
		val sdkVersion = BuildConfig.VERSION_NAME
		return "${context.packageName}/$packageVersion SygicTravelAndroidSdk/$sdkVersion Android/${Build.VERSION.RELEASE}"
	}
}
