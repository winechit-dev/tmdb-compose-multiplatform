package com.wcp.tmdbcmp.presentation.search.mapper

import com.wcp.tmdbcmp.presentation.search.model.SearchMovieUIModel
import com.wcp.tmdbcmp.domain.model.GenreModel
import com.wcp.tmdbcmp.domain.model.MovieModel

fun List<MovieModel>.toSearchMoviesUIModel(genres: List<GenreModel>): List<SearchMovieUIModel> {
    return this.map { it.toSearchMovieUIModel(genres) }
}

private fun MovieModel.toSearchMovieUIModel(genres: List<GenreModel>): SearchMovieUIModel {
    return SearchMovieUIModel(
        id = id,
        posterPath = posterPath,
        name = originalTitle,
        genres = genres.filter { this.genreIds.contains(it.id) }.map { it.name },
        releaseDate = releaseDate,
        overview = overview
    )
}