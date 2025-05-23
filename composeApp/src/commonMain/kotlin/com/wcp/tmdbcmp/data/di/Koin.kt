package com.wcp.tmdbcmp.data.di

import com.wcp.tmdbcmp.data.database.AppDatabase
import com.wcp.tmdbcmp.data.database.getRoomDatabase
import com.wcp.tmdbcmp.data.network.HttpClientFactory
import com.wcp.tmdbcmp.data.network.MovieApi
import com.wcp.tmdbcmp.data.repository.MovieRepositoryImpl
import com.wcp.tmdbcmp.data.repository.SettingRepositoryImpl
import com.wcp.tmdbcmp.domain.repository.MovieRepository
import com.wcp.tmdbcmp.domain.repository.SettingRepository
import com.wcp.tmdbcmp.presentation.MainViewModel
import com.wcp.tmdbcmp.presentation.discover.ui.DiscoverViewModel
import com.wcp.tmdbcmp.presentation.discover.ui.details.MovieDetailsViewModel
import com.wcp.tmdbcmp.presentation.favorites.FavoritesViewModel
import com.wcp.tmdbcmp.presentation.search.SearchViewModel
import com.wcp.tmdbcmp.presentation.settings.SettingsViewModel
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

val dataModule =
    module {
        single<HttpClient> { HttpClientFactory.create() }

        single<MovieApi> { MovieApi(get()) }

        single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

        single<SettingRepository> { SettingRepositoryImpl(get()) }
    }

val databaseModule =
    module {
        single<AppDatabase> { getRoomDatabase(get()) }
    }

val viewModelModule =
    module {
        factoryOf(::MainViewModel)
        factoryOf(::MovieDetailsViewModel)
        factoryOf(::DiscoverViewModel)
        factoryOf(::FavoritesViewModel)
        factoryOf(::SearchViewModel)
        factoryOf(::SettingsViewModel)
    }

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            dataModule,
            viewModelModule,
            platformModule(),
            databaseModule,
        )
    }
}
