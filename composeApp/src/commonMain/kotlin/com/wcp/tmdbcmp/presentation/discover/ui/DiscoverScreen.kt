@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.wcp.tmdbcmp.presentation.discover.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wcp.tmdbcmp.presentation.designsystem.components.AppFilterChip
import com.wcp.tmdbcmp.presentation.designsystem.theme.AppPreviewWithSharedTransitionLayout
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalEntryPadding
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalNavAnimatedVisibilityScope
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalSharedTransitionScope
import com.wcp.tmdbcmp.presentation.designsystem.theme.ThemePreviews
import com.wcp.tmdbcmp.presentation.designsystem.utils.AppSharedElementKey
import com.wcp.tmdbcmp.presentation.designsystem.utils.AppSharedElementType
import com.wcp.tmdbcmp.presentation.designsystem.utils.bounceClick
import com.wcp.tmdbcmp.presentation.designsystem.utils.detailBoundsTransform
import com.wcp.tmdbcmp.presentation.discover.genresPreview
import com.wcp.tmdbcmp.presentation.discover.model.GenreUIModel
import com.wcp.tmdbcmp.presentation.discover.moviesPreview
import com.wcp.tmdbcmp.presentation.discover.subtitle_discover
import com.wcp.tmdbcmp.presentation.discover.title_discover
import com.wcp.tmdbcmp.presentation.ui.MovieItem
import com.wcp.tmdbcmp.presentation.ui.model.MovieUIModel
import com.wcp.tmdbcmp.presentation.utils.ScreenConfig
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tmdb_compose_multiplatform.composeapp.generated.resources.Res
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_search

@Serializable
data object Discover

@Composable
fun DiscoverScreen(onEvent: (DiscoverEvent) -> Unit) {
    val viewModel: DiscoverViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    DiscoverContent(
        uiState = uiState,
        onEvent = { event ->
            when (event) {
                is DiscoverEvent.SelectedGenre -> {
                    viewModel.onSelectedGenre(event.genreId)
                }

                else -> onEvent(event)
            }
        },
    )
}

@Composable
internal fun DiscoverContent(
    modifier: Modifier = Modifier,
    uiState: DiscoverUIState,
    onEvent: (DiscoverEvent) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        val top = LocalEntryPadding.current.calculateTopPadding()
        val bottom = LocalEntryPadding.current.calculateBottomPadding()

        LazyColumn(
            contentPadding = PaddingValues(top = 20.dp, bottom = bottom),
            modifier = Modifier.padding(top = top),
        ) {
            headerSection()

            searchBarSection(onEvent = onEvent)

            genresSection(
                genres = uiState.genres,
                onEvent = onEvent,
            )

            moviesSection(
                title = "Today Trending",
                movies = uiState.trendingTodayMovies,
                onEvent = onEvent,
            )
            moviesSection(
                title = "Popular",
                movies = uiState.popularMovies,
                onEvent = onEvent,
            )
            moviesSection(
                title = "Upcoming",
                movies = uiState.upcomingMovies,
                onEvent = onEvent,
            )
            moviesSection(
                title = "Now Playing",
                movies = uiState.nowPlayingMovies,
                onEvent = onEvent,
            )
            moviesSection(
                title = "Top Rated",
                movies = uiState.topRatedMovies,
                onEvent = onEvent,
            )
        }
    }
}

private fun LazyListScope.headerSection() {
    item {
        Text(
            text = title_discover,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier =
                Modifier
                    .padding(horizontal = 20.dp),
        )
        Text(
            text = subtitle_discover,
            style = MaterialTheme.typography.titleSmall,
            modifier =
                Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 6.dp),
        )
    }
}

private fun LazyListScope.searchBarSection(onEvent: (DiscoverEvent) -> Unit) {
    stickyHeader {
        val sharedTransitionScope =
            LocalSharedTransitionScope.current
                ?: throw IllegalStateException("No Scope found")
        val animatedVisibilityScope =
            LocalNavAnimatedVisibilityScope.current
                ?: throw IllegalStateException("No Scope found")

        with(sharedTransitionScope) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = SurfaceContainerAlpha)),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier
                            .bounceClick()
                            .fillMaxWidth()
                            .padding(20.dp)
                            .sharedBounds(
                                sharedContentState =
                                    rememberSharedContentState(
                                        key =
                                            AppSharedElementKey(
                                                id = "",
                                                type = AppSharedElementType.SearchBar,
                                            ),
                                    ),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = detailBoundsTransform,
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ).clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                            .height(46.dp)
                            .clickable { onEvent(DiscoverEvent.Search) },
                ) {
                    Text(
                        text = "Search",
                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(start = 21.dp),
                    )
                    Icon(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = "",
                        modifier = Modifier.padding(end = 16.dp),
                    )
                }
            }
        }
    }
}

private fun LazyListScope.moviesSection(
    title: String,
    movies: List<MovieUIModel>?,
    onEvent: (DiscoverEvent) -> Unit,
) {
    if (movies?.isEmpty() == true) return

    item {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier =
                Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 16.dp),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier.padding(bottom = 30.dp),
        ) {
            if (movies != null) {
                items(
                    items = movies,
                    key = { it.id },
                    contentType = { "Movie" },
                ) { model ->
                    MovieItem(
                        model = model,
                        type = title,
                        onClick = { it, type ->
                            onEvent(DiscoverEvent.MovieDetails(model = it, type = type))
                        },
                    )
                }
            } else {
                loading()
            }
        }
    }
}

private fun LazyListScope.loading() {
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

private fun LazyListScope.genresSection(
    genres: List<GenreUIModel>,
    onEvent: (DiscoverEvent) -> Unit,
) {
    item {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier.padding(bottom = 30.dp),
        ) {
            items(
                items = genres,
                key = { it.id },
                contentType = { "Genre" },
            ) { genre ->
                AppFilterChip(
                    selected = genre.selected,
                    label = genre.name,
                    onClick = { onEvent(DiscoverEvent.SelectedGenre(genre.id)) },
                )
            }
        }
    }
}

const val SurfaceContainerAlpha = 0.98f

@ThemePreviews
@Composable
internal fun DiscoverContentPreview() {
    AppPreviewWithSharedTransitionLayout {
        DiscoverContent(
            uiState =
                DiscoverUIState(
                    genres = genresPreview,
                    trendingTodayMovies = moviesPreview,
                    upcomingMovies = moviesPreview,
                    popularMovies = moviesPreview,
                    topRatedMovies = moviesPreview,
                    nowPlayingMovies = moviesPreview,
                ),
            onEvent = {},
        )
    }
}
