package com.wcp.tmdbcmp.data.repository

import com.wcp.tmdbcmp.data.database.AppDatabase
import com.wcp.tmdbcmp.data.mapper.toFavoriteEntity
import com.wcp.tmdbcmp.data.mapper.toFavoriteModel
import com.wcp.tmdbcmp.data.mapper.toGenresModel
import com.wcp.tmdbcmp.data.mapper.toMovieDetailsModel
import com.wcp.tmdbcmp.data.mapper.toMoviesModel
import com.wcp.tmdbcmp.data.network.MovieApi
import com.wcp.tmdbcmp.domain.model.FavoriteMovieModel
import com.wcp.tmdbcmp.domain.model.GenresModel
import com.wcp.tmdbcmp.domain.model.MovieDetailsModel
import com.wcp.tmdbcmp.domain.model.MovieModel
import com.wcp.tmdbcmp.domain.model.MoviesModel
import com.wcp.tmdbcmp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val appDatabase: AppDatabase,
) : MovieRepository {
    private val favoriteDao by lazy { appDatabase.favoriteDao() }

    override suspend fun getTrendingTodayMovies(nextPage: Int): Result<MoviesModel> =
        runCatching {
            movieApi.getTrendingTodayMovies(nextPage).toMoviesModel()
        }

    override suspend fun getPopularMovies(nextPage: Int): Result<MoviesModel> =
        runCatching {
            movieApi.getPopularMovies(nextPage).toMoviesModel()
        }

    override suspend fun getUpcomingMovies(nextPage: Int): Result<MoviesModel> =
        runCatching {
            movieApi.getUpcomingMovies(nextPage).toMoviesModel()
        }

    override suspend fun getTopRatedMovies(nextPage: Int): Result<MoviesModel> =
        runCatching {
            movieApi.getTopRatedMovies(nextPage).toMoviesModel()
        }

    override suspend fun getNowPlayingMovies(nextPage: Int): Result<MoviesModel> =
        runCatching {
            movieApi.getNowPlayingMovies(nextPage).toMoviesModel()
        }

    override suspend fun getMovieGenres(): Result<GenresModel> =
        runCatching {
            movieApi.getMovieGenres().toGenresModel()
        }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsModel> =
        runCatching {
            val details = movieApi.getMovieDetails(movieId)
            val cast = movieApi.getCast(movieId).cast
            val recommendations = movieApi.getRecommendations(movieId).results
            details.toMovieDetailsModel(cast, recommendations)
        }

    override suspend fun searchMovie(query: String): Result<List<MovieModel>> =
        runCatching {
            movieApi.searchMovie(query).toMoviesModel().results
        }

    override fun getAllFavorites(): Flow<List<FavoriteMovieModel>> =
        favoriteDao
            .getAllFavorites()
            .map { favorites ->
                favorites.map { it.toFavoriteModel() }
            }

    override fun isFavorite(movieId: Int): Flow<Boolean> = favoriteDao.isFavorite(movieId)

    override suspend fun toggleFavorite(model: FavoriteMovieModel): Result<Unit> =
        runCatching {
            if (model.favorite) {
                favoriteDao.deleteAFavorite(movieId = model.movieId)
            } else {
                favoriteDao.insertFavorite(model.toFavoriteEntity())
            }
        }

    override suspend fun deleteAllFavorites() {
        favoriteDao.deleteAllFavorites()
    }
}
