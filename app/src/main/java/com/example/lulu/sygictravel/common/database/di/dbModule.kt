package com.example.lulu.sygictravel.common.database.di

import android.arch.persistence.room.Room
import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.example.lulu.sygictravel.common.database.Database
import com.example.lulu.sygictravel.common.database.migrations.migration1_2

internal val dbModule = Kodein.Module {
	bind<Database>() with singleton {
		Room.databaseBuilder(
			instance<Context>(),
			Database::class.java,
			"st-sdk-db"
		)
			.addMigrations(
				migration1_2
			)
			.build()
	}
}
