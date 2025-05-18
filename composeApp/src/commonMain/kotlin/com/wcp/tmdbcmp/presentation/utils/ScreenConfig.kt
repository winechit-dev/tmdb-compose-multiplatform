package com.wcp.tmdbcmp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

expect object ScreenConfig {
    @Composable
    fun getScreenWidth(): Dp
}