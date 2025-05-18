package com.wcp.tmdbcmp.presentation.discover.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcp.tmdbcmp.domain.model.FavoriteMovieModel
import com.wcp.tmdbcmp.domain.model.MovieDetailsModel
import com.wcp.tmdbcmp.domain.repository.MovieRepository
import com.wcp.tmdbcmp.presentation.designsystem.utils.UserMessageManager
import com.wcp.tmdbcmp.presentation.ui.model.MovieUIModel
import com.wcp.tmdbcmp.presentation.ui.toMoviesUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id")!!

    private val _uiState = MutableStateFlow(MovieDetailsUIState())
    val uiState: StateFlow<MovieDetailsUIState> =
        combine(
            _uiState,
            repository.isFavorite(id),
        ) { state, favorite ->
            state.copy(favorite = favorite)
        }.stateIn(
            scope = viewModelScope,
            initialValue = MovieDetailsUIState(),
            started = SharingStarted.WhileSubscribed(5000L),
        )

    init {
        getDetails()
    }

    private fun getDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .getMovieDetails(id)
                .onSuccess { details ->
                    _uiState.update { state ->
                        state.copy(
                            loading = false,
                            details = details,
                            recommendations = details.recommendations.toMoviesUIModel(),
                        )
                    }
                }.onFailure { error ->
                    UserMessageManager.showMessage(error.message.toString())
                    _uiState.update { it.copy(loading = false) }
                }
        }
    }

    fun onToggleFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            val details = uiState.value.details ?: return@launch
            repository
                .toggleFavorite(
                    FavoriteMovieModel(
                        movieId = details.id,
                        name = details.originalTitle,
                        posterPath = details.posterPath,
                        favorite = uiState.value.favorite,
                    ),
                ).onFailure { error ->
                    UserMessageManager.showMessage(error.message.toString())
                }
        }
    }
}

data class MovieDetailsUIState(
    val loading: Boolean = true,
    val favorite: Boolean = false,
    val details: MovieDetailsModel? = null,
    val recommendations: List<MovieUIModel> = emptyList(),
)
