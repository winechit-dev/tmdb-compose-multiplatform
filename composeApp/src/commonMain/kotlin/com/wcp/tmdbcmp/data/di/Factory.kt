package com.wcp.tmdbcmp.data.di

import com.wcp.tmdbcmp.data.datastore.PreferencesDataSource

expect class Factory {
    fun createPreferencesDataSource() : PreferencesDataSource
}