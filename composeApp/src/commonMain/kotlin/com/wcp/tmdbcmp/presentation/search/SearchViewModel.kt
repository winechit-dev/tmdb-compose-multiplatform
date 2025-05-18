package com.wcp.tmdbcmp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcp.tmdbcmp.domain.model.GenreModel
import com.wcp.tmdbcmp.domain.repository.MovieRepository
import com.wcp.tmdbcmp.presentation.designsystem.utils.UserMessageManager
import com.wcp.tmdbcmp.presentation.search.mapper.toSearchMoviesUIModel
import com.wcp.tmdbcmp.presentation.search.model.SearchMovieUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()

    init {
        getGenres()
        uiState
            .map { it.query }
            .distinctUntilChanged()
            .debounce(1000L)
            .onEach { query -> if (query.isNotBlank()) searchMovie(query) }
            .launchIn(viewModelScope)
    }


    fun queryChanged(query: String) {
        _uiState.update {
            if (query.isBlank()) {
                it.copy(query = query, movies = null, userMessage = "")
            } else {
                it.copy(query = query, userMessage = "")
            }
        }
    }

    fun updateFocusRequest() {
        _uiState.update {
            it.copy(focusRequested = true)
        }
    }

    private fun searchMovie(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(loading = true) }
            repository
                .searchMovie(query)
                .onFailure { UserMessageManager.showMessage(it.message.toString()) }
                .onSuccess { results ->
                    _uiState.update {
                        val movies = results.toSearchMoviesUIModel(it.genres)
                        it.copy(
                            movies = movies,
                            loading = false,
                            userMessage = if (movies.isEmpty()) "No Result!" else ""
                        )
                    }
                }
        }
    }

    private fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .getMovieGenres()
                .onSuccess { result ->
                    _uiState.update { state ->
                        state.copy(genres = result.genres)
                    }
                }
        }
    }
}

data class SearchUIState(
    val query: String = "",
    val loading: Boolean = false,
    val userMessage: String = "",
    val movies: List<SearchMovieUIModel>? = null,
    val genres: List<GenreModel> = emptyList(),
    val focusRequested: Boolean = false
)