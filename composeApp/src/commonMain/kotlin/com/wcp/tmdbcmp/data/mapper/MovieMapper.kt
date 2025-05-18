package com.wcp.tmdbcmp.data.mapper

import com.wcp.tmdbcmp.data.changeFormat
import com.wcp.tmdbcmp.data.database.entities.FavoriteEntity
import com.wcp.tmdbcmp.data.localDateTime
import com.wcp.tmdbcmp.data.millis
import com.wcp.tmdbcmp.data.network.model.CastResponse
import com.wcp.tmdbcmp.data.network.model.GenresResponse
import com.wcp.tmdbcmp.data.network.model.MovieDetailsResponse
import com.wcp.tmdbcmp.data.network.model.MovieResponse
import com.wcp.tmdbcmp.data.network.model.MoviesResponse
import com.wcp.tmdbcmp.domain.model.CastModel
import com.wcp.tmdbcmp.domain.model.FavoriteMovieModel
import com.wcp.tmdbcmp.domain.model.GenreModel
import com.wcp.tmdbcmp.domain.model.GenresModel
import com.wcp.tmdbcmp.domain.model.MovieDetailsModel
import com.wcp.tmdbcmp.domain.model.MovieModel
import com.wcp.tmdbcmp.domain.model.MoviesModel

fun MoviesResponse.toMoviesModel(): MoviesModel =
    MoviesModel(
        page = page,
        results = results.map { it.toMovieModel() },
        totalPages = totalPages,
        totalResults = totalResults,
    )

fun MovieResponse.toMovieModel(): MovieModel =
    MovieModel(
        adult = adult,
        backdropPath = backdropPath.orEmpty(),
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath.createImageUrl(),
        releaseDate = releaseDate.changeFormat("yyyy-MM-dd", "dd MMM yyyy"),
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )

fun GenresResponse.toGenresModel(): GenresModel =
    GenresModel(
        genres =
            this.genres.map {
                GenreModel(
                    id = it.id,
                    name = it.name,
                )
            },
    )

fun MovieDetailsResponse.toMovieDetailsModel(
    cast: List<CastResponse>,
    recommendations: List<MovieResponse>,
): MovieDetailsModel =
    MovieDetailsModel(
        adult = adult ?: false,
        backdropPath = backdropPath.orEmpty(),
        budget = budget ?: 0,
        genres =
            genres
                ?.map {
                    GenreModel(
                        id = it.id,
                        name = it.name,
                    )
                }.orEmpty(),
        homepage = homepage.orEmpty(),
        id = id!!,
        imdbId = imdbId.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = posterPath.createImageUrl(),
        releaseDate = releaseDate.changeFormat("yyyy-MM-dd", "dd MMM yyyy"),
        revenue = revenue ?: 0,
        runtime = runtime ?: 0,
        status = status.orEmpty(),
        tagline = tagline.orEmpty(),
        title = title.orEmpty(),
        video = video ?: false,
        voteAverage = (voteAverage ?: 0.0).toFloat(),
        voteCount = voteCount ?: 0,
        cast = cast.toCast(),
        recommendations = recommendations.map { it.toMovieModel() },
    )

fun List<CastResponse>.toCast(): List<CastModel> =
    map {
        CastModel(
            castId = it.castId,
            id = it.id,
            profilePath = it.profilePath.createImageUrl(),
            originalName = it.originalName,
        )
    }

fun FavoriteEntity.toFavoriteModel(): FavoriteMovieModel =
    FavoriteMovieModel(
        movieId = movieId,
        name = name,
        favorite = favorite,
        posterPath = posterPath,
    )

fun FavoriteMovieModel.toFavoriteEntity(): FavoriteEntity =
    FavoriteEntity(
        movieId = movieId,
        name = name,
        favorite = true,
        posterPath = posterPath,
        createdAt = localDateTime.millis,
    )

fun String?.createImageUrl(): String = "https://image.tmdb.org/t/p/w500/$this"
