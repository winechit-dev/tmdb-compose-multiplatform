package com.wcp.tmdbcmp.data

import android.content.Context
import com.wcp.tmdbcmp.data.datastore.PreferencesDataSource

fun createDataStore(context: Context): PreferencesDataSource =
    PreferencesDataSource {
        context.filesDir.resolve("settings.json").absolutePath
    }
