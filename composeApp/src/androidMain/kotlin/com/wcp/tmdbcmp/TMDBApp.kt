package com.wcp.tmdbcmp

import android.app.Application
import com.wcp.tmdbcmp.data.di.initKoin
import org.koin.android.ext.koin.androidContext

class TMDBApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TMDBApp)
        }
    }
}
