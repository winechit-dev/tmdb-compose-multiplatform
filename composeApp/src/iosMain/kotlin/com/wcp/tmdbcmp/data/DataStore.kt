package com.wcp.tmdbcmp.data

import com.wcp.tmdbcmp.data.datastore.PreferencesDataSource

fun createDataStore(): PreferencesDataSource =
    PreferencesDataSource {
        "${documentDirectory()}/settings.json"
    }
