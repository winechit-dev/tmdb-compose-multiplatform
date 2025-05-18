package com.wcp.tmdbcmp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcp.tmdbcmp.domain.model.FavoriteMovieModel
import com.wcp.tmdbcmp.domain.repository.MovieRepository
import com.wcp.tmdbcmp.presentation.designsystem.utils.UserMessageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    val favorites: StateFlow<List<FavoriteMovieModel>> = repository
        .getAllFavorites()
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5000L)
        )

    fun onToggleFavorite(model: FavoriteMovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .toggleFavorite(model)
                .onFailure { error ->
                    UserMessageManager.showMessage(error.message.toString())
                }
        }
    }
}
