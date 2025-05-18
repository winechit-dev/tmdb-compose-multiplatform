package com.wcp.tmdbcmp.presentation.ui

import com.wcp.tmdbcmp.domain.model.MovieModel
import com.wcp.tmdbcmp.presentation.ui.model.MovieUIModel

fun List<MovieModel>.toMoviesUIModel(genreId: Int? = 100): List<MovieUIModel> {
    return this
        .map { it.toMovieUIModel() }
        .filter { if (genreId != 100) it.genreIds.contains(genreId) else true }
}

private fun MovieModel.toMovieUIModel(): MovieUIModel {
    return MovieUIModel(
        id = id,
        posterPath = posterPath,
        name = originalTitle,
        genreIds = genreIds
    )
}