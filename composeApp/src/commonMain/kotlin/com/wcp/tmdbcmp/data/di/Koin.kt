package com.wcp.tmdbcmp.data.di

import com.wcp.tmdbcmp.data.database.AppDatabase
import com.wcp.tmdbcmp.data.database.getRoomDatabase
import com.wcp.tmdbcmp.data.network.HttpClientFactory
import com.wcp.tmdbcmp.data.network.MovieApi
import com.wcp.tmdbcmp.data.repository.MovieRepositoryImpl
import com.wcp.tmdbcmp.domain.repository.MovieRepository
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

val dataModule =
    module {
        single<HttpClient> { HttpClientFactory.create() }

        single<MovieApi> { MovieApi(get()) }

        single<MovieApi> { MovieApi(get()) }

        single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    }


val databaseModule =
    module {
        single<AppDatabase> { getRoomDatabase(get()) }
    }

fun initKoin (config : KoinAppDeclaration?= null){
    startKoin {
        config?.invoke(this)
        modules(
            dataModule,
            platformModule(),
            databaseModule
        )
    }
}