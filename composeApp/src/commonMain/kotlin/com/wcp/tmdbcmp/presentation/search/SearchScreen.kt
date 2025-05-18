@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.wcp.tmdbcmp.presentation.search

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.wcp.tmdbcmp.presentation.designsystem.components.AppChip
import com.wcp.tmdbcmp.presentation.designsystem.components.AppIconButton
import com.wcp.tmdbcmp.presentation.designsystem.components.AppSearchBar
import com.wcp.tmdbcmp.presentation.designsystem.theme.AppPreviewWithSharedTransitionLayout
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalNavAnimatedVisibilityScope
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalSharedTransitionScope
import com.wcp.tmdbcmp.presentation.designsystem.theme.ThemePreviews
import com.wcp.tmdbcmp.presentation.designsystem.utils.AppSharedElementKey
import com.wcp.tmdbcmp.presentation.designsystem.utils.AppSharedElementType
import com.wcp.tmdbcmp.presentation.designsystem.utils.bounceClick
import com.wcp.tmdbcmp.presentation.designsystem.utils.detailBoundsTransform
import com.wcp.tmdbcmp.presentation.search.model.SearchMovieUIModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import tmdb_compose_multiplatform.composeapp.generated.resources.Res
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_back
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_clear
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_search

@Serializable
data object Search

@Composable
fun SearchScreen(onEvent: (SearchEvent) -> Unit) {
    val viewModel: SearchViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    SearchContent(
        uiState = uiState,
        onEvent = { event ->
            when (event) {
                is SearchEvent.QueryChanged -> {
                    viewModel.queryChanged(event.query)
                }

                is SearchEvent.FocusRequested -> {
                    viewModel.updateFocusRequest()
                }

                else -> onEvent(event)
            }
        },
    )
}

@Composable
internal fun SearchContent(
    modifier: Modifier = Modifier,
    uiState: SearchUIState,
    onEvent: (SearchEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        if (!uiState.focusRequested) {
            focusRequester.requestFocus()
            onEvent(SearchEvent.FocusRequested)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .statusBarsPadding()
                        .padding(top = 20.dp),
            ) {
                AppIconButton(
                    icon = Res.drawable.ic_back,
                    onClick = {
                        keyboard?.hide()
                        onEvent(SearchEvent.NavigateUp)
                    },
                )
                AppSearchBar(
                    query = uiState.query,
                    onQueryChanged = { onEvent(SearchEvent.QueryChanged(it)) },
                    trailingIcon = {
                        if (uiState.query.isNotBlank()) {
                            AppIconButton(
                                icon = Res.drawable.ic_clear,
                                containerColor = Color.Transparent,
                                onClick = {
                                    keyboard?.show()
                                    onEvent(SearchEvent.QueryChanged(""))
                                },
                            )
                        } else {
                            AppIconButton(
                                icon = Res.drawable.ic_search,
                                containerColor = Color.Transparent,
                                onClick = {},
                            )
                        }
                    },
                    modifier =
                        Modifier
                            .focusRequester(focusRequester)
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                )
            }
        },
    ) { innerPadding ->
        if (uiState.loading) {
            Loading(modifier = Modifier.padding(innerPadding))
            return@Scaffold
        }
        LazyColumn(
            modifier =
                Modifier
                    .imePadding()
                    .padding(top = innerPadding.calculateTopPadding() + 10.dp)
                    .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            items(
                items = uiState.movies.orEmpty(),
                key = { it.id },
                contentType = { "Search Item" },
            ) { model ->
                SearchItem(
                    model = model,
                    onEvent = {
                        focusManager.clearFocus()
                        onEvent(it)
                    },
                )
            }
            item {
                if (uiState.userMessage.isNotBlank()) {
                    Text(
                        text = uiState.userMessage,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(20.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SearchItem(
    modifier: Modifier = Modifier,
    model: SearchMovieUIModel,
    onEvent: (SearchEvent) -> Unit,
) {
    val sharedTransitionScope =
        LocalSharedTransitionScope.current
            ?: throw IllegalStateException("No Scope found")
    val animatedVisibilityScope =
        LocalNavAnimatedVisibilityScope.current
            ?: throw IllegalStateException("No Scope found")

    with(sharedTransitionScope) {
        Card(
            modifier =
                modifier
                    .bounceClick()
                    .sharedBounds(
                        sharedContentState =
                            rememberSharedContentState(
                                key =
                                    AppSharedElementKey(
                                        id = model.id.toString(),
                                        type = AppSharedElementType.Bounds,
                                    ),
                            ),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = detailBoundsTransform,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ).fillMaxWidth(),
            onClick = { onEvent(SearchEvent.MovieDetails(model)) },
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                AsyncImage(
                    model = model.posterPath,
                    contentDescription = "poster",
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .weight(0.8f)
                            .size(124.dp, 200.dp)
                            .clip(RoundedCornerShape(topStart = 12.0.dp, bottomStart = 12.0.dp)),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier =
                        Modifier
                            .padding(vertical = 10.dp)
                            .weight(1.2f),
                ) {
                    Text(
                        text = model.name,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 20.dp),
                        fontWeight = FontWeight.SemiBold,
                    )
                    if (model.overview.isNotBlank()) {
                        Text(
                            text = model.overview,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(end = 20.dp),
                        )
                    }

                    if (model.releaseDate.isNotBlank()) {
                        Text(
                            text = model.releaseDate,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(end = 30.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(
                            items = model.genres,
                        ) { genre ->
                            AppChip(label = genre)
                        }
                    }
                }
            }
        }
    }
}

@ThemePreviews
@Composable
internal fun SearchContentPreview() {
    AppPreviewWithSharedTransitionLayout {
        SearchContent(
            uiState =
                SearchUIState(
                    movies =
                        (1..8).toList().map {
                            SearchMovieUIModel(
                                id = it,
                                name = "Movie $it",
                                posterPath = "",
                                genres = listOf("Genre1", "Genre2", "Genre3"),
                                releaseDate = "12/12/1996",
                                overview = "The director's cut version of the final three episodes of the Ohsama Sentai King-Ohger TV series, recut into one movie with 10 minutes of unreleased scenes added in.",
                            )
                        },
                ),
            onEvent = {},
        )
    }
}
