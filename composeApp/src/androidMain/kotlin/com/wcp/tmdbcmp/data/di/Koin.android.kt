package com.wcp.tmdbcmp.data.di

import androidx.room.RoomDatabase
import com.wcp.tmdbcmp.data.createDataStore
import com.wcp.tmdbcmp.data.database.AppDatabase
import com.wcp.tmdbcmp.data.datastore.PreferencesDataSource
import com.wcp.tmdbcmp.data.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<PreferencesDataSource> {
        createDataStore(get())
    }

    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder(get())
    }
}
