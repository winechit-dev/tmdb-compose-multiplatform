package com.wcp.tmdbcmp.data.di

import com.wcp.tmdbcmp.data.network.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val dataModule =
    module {
        single<HttpClient> {
            HttpClientFactory.create()
        }
    }
