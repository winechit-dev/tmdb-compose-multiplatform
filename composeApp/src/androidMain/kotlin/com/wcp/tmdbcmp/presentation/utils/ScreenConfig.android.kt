package com.wcp.tmdbcmp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

actual object ScreenConfig {
    @Composable
    actual fun getScreenWidth(): Dp = LocalConfiguration.current.screenWidthDp.dp
}
