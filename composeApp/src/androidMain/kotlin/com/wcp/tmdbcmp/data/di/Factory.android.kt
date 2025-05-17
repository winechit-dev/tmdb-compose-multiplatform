package com.wcp.tmdbcmp.data.di

import android.content.Context
import com.wcp.tmdbcmp.data.datastore.PreferencesDataSource

actual class Factory(private val context : Context) {
    actual fun createPreferencesDataSource(): PreferencesDataSource {
        return PreferencesDataSource {
            context.filesDir.resolve("settings.json").absolutePath
        }
    }
}