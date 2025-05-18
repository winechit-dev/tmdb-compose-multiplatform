package com.wcp.tmdbcmp.presentation.settings

import com.wcp.tmdbcmp.domain.model.DarkThemeConfig
import com.wcp.tmdbcmp.domain.model.ThemeBrand


sealed interface SettingsEvent {
    data class ChangeThemeBrand(val themeBrand: ThemeBrand) : SettingsEvent
    data class ChangeDarkThemeConfig(val darkThemeConfig: DarkThemeConfig) : SettingsEvent
    data class ChangeDynamicColorPreference(val useDynamicColor: Boolean) : SettingsEvent
}