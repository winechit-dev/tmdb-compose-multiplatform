package com.wcp.tmdbcmp.data.di

import com.wcp.tmdbcmp.data.datastore.PreferencesDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single<PreferencesDataSource> {
            Factory().createPreferencesDataSource()
        }
    }
}