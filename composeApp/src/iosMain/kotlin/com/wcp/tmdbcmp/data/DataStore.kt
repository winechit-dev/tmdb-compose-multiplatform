package com.wcp.tmdbcmp.data

import com.wcp.tmdbcmp.data.datastore.PreferencesDataSource
import com.wcp.tmdbcmp.data.di.fileDirectory

fun createDataStore(): PreferencesDataSource {
    return PreferencesDataSource {
        "${documentDirectory()}/settings.json"
    }
}