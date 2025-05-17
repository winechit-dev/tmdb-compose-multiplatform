package com.wcp.tmdbcmp.data.datastore

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.wcp.tmdbcmp.data.datastore.model.DarkThemeConfigResponse
import com.wcp.tmdbcmp.data.datastore.model.SettingsDataResponse
import com.wcp.tmdbcmp.data.datastore.model.ThemeBrandResponse
import com.wcp.tmdbcmp.data.datastore.serializer.SettingDataSerializer
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

class PreferencesDataSource(
    private val producePath: () -> String,
) {
    private val settingsDataStore =
        DataStoreFactory.create(
            storage =
                OkioStorage(
                    fileSystem = FileSystem.SYSTEM,
                    serializer = SettingDataSerializer,
                    producePath = {
                        producePath().toPath()
                    },
                ),
        )

    val settingsDataFlow: Flow<SettingsDataResponse>
        get() = settingsDataStore.data

    suspend fun setThemeBrand(themeBrand: ThemeBrandResponse) {
        settingsDataStore.updateData { it.copy(themeBrand = themeBrand) }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        settingsDataStore.updateData {
            it.copy(useDynamicColor = useDynamicColor)
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfigResponse: DarkThemeConfigResponse) {
        settingsDataStore.updateData {
            it.copy(darkThemeConfig = darkThemeConfigResponse)
        }
    }
}
