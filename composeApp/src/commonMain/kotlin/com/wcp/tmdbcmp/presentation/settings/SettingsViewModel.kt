package com.wcp.tmdbcmp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcp.tmdbcmp.domain.model.DarkThemeConfig
import com.wcp.tmdbcmp.domain.model.SettingsDataModel
import com.wcp.tmdbcmp.domain.model.ThemeBrand
import com.wcp.tmdbcmp.domain.repository.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingRepository: SettingRepository
) : ViewModel() {

    val settings = settingRepository
        .settingsFlow
        .stateIn(
            scope = viewModelScope,
            initialValue = SettingsDataModel(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                useDynamicColor = true
            ),
            started = SharingStarted.WhileSubscribed(5000L)
        )

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeThemeBrand -> setThemeBrand(event.themeBrand)
            is SettingsEvent.ChangeDarkThemeConfig -> setDarkThemeConfig(event.darkThemeConfig)
            is SettingsEvent.ChangeDynamicColorPreference -> setDynamicColorPreference(event.useDynamicColor)
        }
    }

    private fun setThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setThemeBrand(themeBrand)
        }
    }

    private fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    private fun setDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setDynamicColorPreference(useDynamicColor)
        }
    }
}