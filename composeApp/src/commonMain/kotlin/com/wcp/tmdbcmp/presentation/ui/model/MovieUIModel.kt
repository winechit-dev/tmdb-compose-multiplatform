package com.wcp.tmdbcmp.presentation.ui.model

data class MovieUIModel(
    val id: Int,
    val name: String,
    val posterPath: String,
    val genreIds: List<Int>,
)
