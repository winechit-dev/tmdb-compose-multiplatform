package com.wcp.tmdbcmp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen

actual object ScreenConfig {
    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual fun getScreenWidth(): Dp =
        UIScreen.mainScreen.bounds
            .useContents { size.width }
            .dp
}
