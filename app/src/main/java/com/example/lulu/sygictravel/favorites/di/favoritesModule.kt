package com.example.lulu.sygictravel.favorites.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.example.lulu.sygictravel.common.database.Database
import com.example.lulu.sygictravel.favorites.facade.FavoritesFacade
import com.example.lulu.sygictravel.favorites.model.daos.FavoriteDao
import com.example.lulu.sygictravel.favorites.service.FavoriteService

internal val favoritesModule = Kodein.Module {
	bind<FavoritesFacade>() with singleton { FavoritesFacade(instance<FavoriteService>()) }
	bind<FavoriteService>() with singleton { FavoriteService(instance<FavoriteDao>()) }
	bind<FavoriteDao>() with singleton { instance<Database>().favoriteDao() }
}
