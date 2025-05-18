package com.wcp.tmdbcmp.presentation

sealed interface MainEvent {
    data object Idle : MainEvent

    data class UserMessage(
        val message: String,
    ) : MainEvent
}
