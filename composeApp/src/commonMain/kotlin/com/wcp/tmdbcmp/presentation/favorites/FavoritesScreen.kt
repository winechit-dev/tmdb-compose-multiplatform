@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.wcp.tmdbcmp.presentation.favorites

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.wcp.tmdbcmp.domain.model.FavoriteMovieModel
import com.wcp.tmdbcmp.presentation.designsystem.components.AppIconButton
import com.wcp.tmdbcmp.presentation.designsystem.theme.AppPreviewWithSharedTransitionLayout
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalEntryPadding
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalNavAnimatedVisibilityScope
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalSharedTransitionScope
import com.wcp.tmdbcmp.presentation.designsystem.theme.ThemePreviews
import com.wcp.tmdbcmp.presentation.designsystem.utils.AppSharedElementKey
import com.wcp.tmdbcmp.presentation.designsystem.utils.AppSharedElementType
import com.wcp.tmdbcmp.presentation.designsystem.utils.bounceClick
import com.wcp.tmdbcmp.presentation.designsystem.utils.detailBoundsTransform
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import tmdb_compose_multiplatform.composeapp.generated.resources.Res
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_favorite_off
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_favorite_on

@Serializable
data object Favorites

@Composable
fun FavoritesScreen(
    onNavigateMovieDetails: (model: FavoriteMovieModel, type: String) -> Unit
) {
    val viewModel: FavoritesViewModel = koinViewModel()
    val favorites by viewModel.favorites.collectAsState()

    FavoritesContent(
        favorites = favorites,
        onFavoriteToggle = viewModel::onToggleFavorite,
        onNavigateMovieDetails = onNavigateMovieDetails
    )
}

@Composable
internal fun FavoritesContent(
    modifier: Modifier = Modifier,
    favorites: List<FavoriteMovieModel>,
    onFavoriteToggle: (FavoriteMovieModel) -> Unit = {},
    onNavigateMovieDetails: (model: FavoriteMovieModel, type: String) -> Unit = { _, _ -> }
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Text(
                text = "Favorites",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            )
        }
    ) { innerPadding ->
        val top = innerPadding.calculateTopPadding() + 20.dp
        val bottom =
            LocalEntryPadding.current.calculateBottomPadding() + innerPadding.calculateBottomPadding()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = bottom),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = top)
                .padding(horizontal = 20.dp)
        ) {
            items(
                items = favorites,
                key = { it.movieId },
                contentType = { "" }
            ) { model ->
                FavoriteMovieItem(
                    model = model,
                    onFavoriteToggle = onFavoriteToggle,
                    onNavigateMovieDetails = onNavigateMovieDetails
                )
            }
        }
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Favorites!"
                )
            }
        }
    }
}

@Composable
private fun FavoriteMovieItem(
    modifier: Modifier = Modifier,
    model: FavoriteMovieModel,
    onFavoriteToggle: (FavoriteMovieModel) -> Unit,
    onNavigateMovieDetails: (model: FavoriteMovieModel, type: String) -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No Scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No Scope found")
    val hapticFeedback = LocalHapticFeedback.current

    with(sharedTransitionScope) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(124f / 188f)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = AppSharedElementKey(
                            id = model.movieId.toString() + AppSharedElementType.Favorite,
                            type = AppSharedElementType.Bounds
                        )
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = detailBoundsTransform,
                    enter = fadeIn(),
                    exit = fadeOut()
                ),
            onClick = { onNavigateMovieDetails(model, AppSharedElementType.Favorite.toString()) }
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = model.posterPath,
                    contentDescription = "poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )
                AppIconButton(
                    icon = if (model.favorite) Res.drawable.ic_favorite_on else Res.drawable.ic_favorite_off,
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        onFavoriteToggle(model)
                    },
                    modifier = Modifier
                        .bounceClick()
                        .padding(8.dp)
                )
            }
        }
    }

}


@ThemePreviews
@Composable
internal fun FavoritesContentPreview() {
    AppPreviewWithSharedTransitionLayout {
        FavoritesContent(
            favorites = (1..8).toList().map {
                FavoriteMovieModel(
                    movieId = it,
                    name = "FavoriteMovie $it",
                    posterPath = "",
                    favorite = it == 1
                )
            }
        )
    }
}