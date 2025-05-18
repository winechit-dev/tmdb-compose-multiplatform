package com.wcp.tmdbcmp.presentation

import androidx.lifecycle.viewModelScope
import com.wcp.tmdbcmp.domain.model.DarkThemeConfig
import com.wcp.tmdbcmp.domain.model.SettingsDataModel
import com.wcp.tmdbcmp.domain.model.ThemeBrand
import com.wcp.tmdbcmp.domain.repository.SettingRepository
import com.wcp.tmdbcmp.presentation.base.BaseViewModel
import com.wcp.tmdbcmp.presentation.designsystem.utils.UserMessageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    repository: SettingRepository,
) : BaseViewModel<MainEvent>() {
    private val userMessageManager by lazy { UserMessageManager }

    val uiState =
        repository.settingsFlow
            .map { MainUIState(settings = it) }
            .stateIn(
                scope = viewModelScope,
                initialValue = MainUIState(),
                started = SharingStarted.WhileSubscribed(5000L),
            )

    fun userMessageShown() {
        userMessageManager.userMessageShown()
    }

    init {
        handelUserMessage()
    }

    private fun handelUserMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            userMessageManager
                .messages
                .collectLatest { message ->
                    if (message != null) {
                        emitEvent(MainEvent.UserMessage(message))
                    } else {
                        emitEvent(MainEvent.Idle)
                    }
                }
        }
    }
}

data class MainUIState(
    val settings: SettingsDataModel =
        SettingsDataModel(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            useDynamicColor = true,
        ),
)
