package com.example.lulu.sygictravel.common.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.squareup.moshi.Moshi
import com.example.lulu.sygictravel.common.api.SygicTravelApiClient
import com.example.lulu.sygictravel.common.api.interceptors.HeadersInterceptor
import com.example.lulu.sygictravel.common.api.interceptors.LocaleInterceptor
import com.example.lulu.sygictravel.common.api.interceptors.LocaleInterceptor.Companion.LOCALE_PLACEHOLDER
import com.example.lulu.sygictravel.session.service.AuthStorageService
import com.example.lulu.sygictravel.utils.UserAgentUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal val sygicTravelApiModule = Kodein.Module {
	bind<HeadersInterceptor>() with singleton {
		HeadersInterceptor(
			authStorageService = provider<AuthStorageService>(),
			apiKey = instance("apiKey"),
			userAgent = UserAgentUtil.createUserAgent(instance<Context>())
		)
	}

	bind<LocaleInterceptor>() with singleton { LocaleInterceptor() }

	bind<OkHttpClient>("sygicTravelHttpClient") with singleton {
		val builder = OkHttpClient.Builder()
			.addInterceptor(instance<HeadersInterceptor>())
			.addInterceptor(instance<LocaleInterceptor>())

		if (instance("debugMode")) {
			builder.addInterceptor(instance<HttpLoggingInterceptor>())
		}

		builder
			.cache(instance<Cache>())
			.readTimeout(60, TimeUnit.SECONDS)
			.build()
	}

	bind<Retrofit>("sygicTravelApiRetrofit") with singleton {
		Retrofit.Builder()
			.client(instance<OkHttpClient>("sygicTravelHttpClient"))
			.baseUrl(instance<String>("sygicTravelApiUrl") + "/$LOCALE_PLACEHOLDER/")
			.addConverterFactory(MoshiConverterFactory.create(instance<Moshi>()).withNullSerialization())
			.build()
	}

	bind<SygicTravelApiClient>() with singleton {
		instance<Retrofit>("sygicTravelApiRetrofit").create(SygicTravelApiClient::class.java)
	}
}
