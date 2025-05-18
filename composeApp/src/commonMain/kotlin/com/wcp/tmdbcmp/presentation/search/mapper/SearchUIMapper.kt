package com.wcp.tmdbcmp.presentation.search.mapper

import com.wcp.tmdbcmp.domain.model.GenreModel
import com.wcp.tmdbcmp.domain.model.MovieModel
import com.wcp.tmdbcmp.presentation.search.model.SearchMovieUIModel

fun List<MovieModel>.toSearchMoviesUIModel(genres: List<GenreModel>): List<SearchMovieUIModel> =
    this.map {
        it.toSearchMovieUIModel(genres)
    }

private fun MovieModel.toSearchMovieUIModel(genres: List<GenreModel>): SearchMovieUIModel =
    SearchMovieUIModel(
        id = id,
        posterPath = posterPath,
        name = originalTitle,
        genres = genres.filter { this.genreIds.contains(it.id) }.map { it.name },
        releaseDate = releaseDate,
        overview = overview,
    )
