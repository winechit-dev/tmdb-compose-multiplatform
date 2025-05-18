package com.wcp.tmdbcmp.domain.model

data class MoviesModel(
    val page: Int,
    val results: List<MovieModel>,
    val totalPages: Int,
    val totalResults: Int,
)
