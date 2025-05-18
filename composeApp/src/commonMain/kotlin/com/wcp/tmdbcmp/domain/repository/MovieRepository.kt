package com.wcp.tmdbcmp.domain.repository

import com.wcp.tmdbcmp.domain.model.FavoriteMovieModel
import com.wcp.tmdbcmp.domain.model.GenresModel
import com.wcp.tmdbcmp.domain.model.MovieDetailsModel
import com.wcp.tmdbcmp.domain.model.MovieModel
import com.wcp.tmdbcmp.domain.model.MoviesModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrendingTodayMovies(nextPage: Int): Result<MoviesModel>

    suspend fun getPopularMovies(nextPage: Int): Result<MoviesModel>

    suspend fun getUpcomingMovies(nextPage: Int): Result<MoviesModel>

    suspend fun getTopRatedMovies(nextPage: Int): Result<MoviesModel>

    suspend fun getNowPlayingMovies(nextPage: Int): Result<MoviesModel>

    suspend fun getMovieGenres(): Result<GenresModel>

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsModel>

    suspend fun searchMovie(query: String): Result<List<MovieModel>>

    fun getAllFavorites(): Flow<List<FavoriteMovieModel>>

    fun isFavorite(movieId: Int): Flow<Boolean>

    suspend fun toggleFavorite(model: FavoriteMovieModel): Result<Unit>

    suspend fun deleteAllFavorites()
}
