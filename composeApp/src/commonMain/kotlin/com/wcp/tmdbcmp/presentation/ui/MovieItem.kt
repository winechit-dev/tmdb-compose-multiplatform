@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.wcp.tmdbcmp.presentation.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.wcp.tmdbcmp.presentation.designsystem.theme.AppPreviewWrapper
import com.wcp.tmdbcmp.presentation.ui.model.MovieUIModel
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalNavAnimatedVisibilityScope
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalSharedTransitionScope
import com.wcp.tmdbcmp.presentation.designsystem.theme.ThemePreviews
import com.wcp.tmdbcmp.presentation.designsystem.utils.AppSharedElementKey
import com.wcp.tmdbcmp.presentation.designsystem.utils.AppSharedElementType
import com.wcp.tmdbcmp.presentation.designsystem.utils.bounceClick
import com.wcp.tmdbcmp.presentation.designsystem.utils.detailBoundsTransform
import com.wcp.tmdbcmp.presentation.utils.ScreenConfig

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    model: MovieUIModel,
    type: String = "",
    onClick: (MovieUIModel, String) -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No Scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No Scope found")

    with(sharedTransitionScope) {
        Card(
            modifier = modifier
                .bounceClick()
                .widthIn(max = (ScreenConfig.getScreenWidth() / 3))
                .aspectRatio(124f / 188f)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = AppSharedElementKey(
                            id = model.id.toString() + type,
                            type = AppSharedElementType.Bounds
                        )
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = detailBoundsTransform,
                    enter = fadeIn(),
                    exit = fadeOut()
                ),
            onClick = { onClick(model, type) }
        ) {
            AsyncImage(
                model = model.posterPath,
                contentDescription = "poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }

}

@ThemePreviews
@Composable
private fun MovieItemPreview() {
    AppPreviewWrapper {
        MovieItem(
            model = MovieUIModel(
                id = 1,
                posterPath = "",
                name = "",
                genreIds = emptyList()
            ),
            onClick = { _, _ -> }
        )
    }
}