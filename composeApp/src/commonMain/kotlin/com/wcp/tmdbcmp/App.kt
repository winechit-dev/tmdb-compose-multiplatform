
@file:Suppress("ktlint")

package com.wcp.tmdbcmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.wcp.tmdbcmp.domain.model.DarkThemeConfig
import com.wcp.tmdbcmp.presentation.MainEvent
import com.wcp.tmdbcmp.presentation.MainUIState
import com.wcp.tmdbcmp.presentation.MainViewModel
import com.wcp.tmdbcmp.presentation.designsystem.components.AppAlertDialog
import com.wcp.tmdbcmp.presentation.designsystem.theme.MovieQuestTheme
import com.wcp.tmdbcmp.presentation.discover.ui.Discover
import com.wcp.tmdbcmp.presentation.navigation.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel : MainViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    MovieQuestTheme {
        val darkTheme = shouldUseDarkTheme(uiState)
        val navController: NavHostController = rememberNavController()
        NavGraph(
            startDestination = Discover,
            navController = navController,
            darkTheme = darkTheme,
            dynamicColor = shouldDisableDynamicTheming(uiState),
        ) {
            HandleUserMessage(viewModel = viewModel)
        }
    }
}


@Composable
private fun HandleUserMessage(viewModel: MainViewModel) {
    var userMessage by remember { mutableStateOf<String?>(null) }
    val event by viewModel.event.collectAsState(initial = null)

    LaunchedEffect(event) {
        when (event) {
            is MainEvent.UserMessage -> {
                userMessage = (event as MainEvent.UserMessage).message
            }

            else -> {}
        }
    }

    if (userMessage != null) {
        AppAlertDialog(
            body = userMessage!!,
            confirmButton = {
                userMessage = null
                viewModel.userMessageShown()
            }
        )
    }
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainUIState,
): Boolean = when (uiState.settings.darkThemeConfig) {
    DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    DarkThemeConfig.LIGHT -> false
    DarkThemeConfig.DARK -> true
}


@Composable
private fun shouldDisableDynamicTheming(
    uiState: MainUIState,
): Boolean = uiState.settings.useDynamicColor


