package com.wcp.tmdbcmp.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.wcp.tmdbcmp.presentation.utils.ScreenConfig

fun LazyListScope.moviesLoading() {
    items(
        items = (1..3).toList(),
        key = { it },
        contentType = { "loading" },
    ) {
        Box(
            modifier =
                Modifier
                    .widthIn(max = (ScreenConfig.getScreenWidth() / 3))
                    .aspectRatio(124f / 188f),
        )
    }
}
