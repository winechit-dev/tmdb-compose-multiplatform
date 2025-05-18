package com.wcp.tmdbcmp.data.repository

import com.wcp.tmdbcmp.data.datastore.PreferencesDataSource
import com.wcp.tmdbcmp.data.datastore.model.DarkThemeConfigResponse
import com.wcp.tmdbcmp.data.datastore.model.ThemeBrandResponse
import com.wcp.tmdbcmp.data.mapper.toSettingsDataModel
import com.wcp.tmdbcmp.domain.model.DarkThemeConfig
import com.wcp.tmdbcmp.domain.model.SettingsDataModel
import com.wcp.tmdbcmp.domain.model.ThemeBrand
import com.wcp.tmdbcmp.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource,
) : SettingRepository {
    override val settingsFlow: Flow<SettingsDataModel>
        get() =
            preferencesDataSource
                .settingsDataFlow
                .map { it.toSettingsDataModel() }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        preferencesDataSource.setThemeBrand(
            when (themeBrand) {
                ThemeBrand.DEFAULT -> ThemeBrandResponse.DEFAULT
                ThemeBrand.ANDROID -> ThemeBrandResponse.ANDROID
            },
        )
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        preferencesDataSource.setDarkThemeConfig(
            when (darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigResponse.FOLLOW_SYSTEM
                DarkThemeConfig.LIGHT -> DarkThemeConfigResponse.LIGHT
                DarkThemeConfig.DARK -> DarkThemeConfigResponse.DARK
            },
        )
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        preferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }
}
