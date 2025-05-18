package com.wcp.tmdbcmp.domain.repository

import com.wcp.tmdbcmp.domain.model.DarkThemeConfig
import com.wcp.tmdbcmp.domain.model.SettingsDataModel
import com.wcp.tmdbcmp.domain.model.ThemeBrand
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val settingsFlow: Flow<SettingsDataModel>

    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}
