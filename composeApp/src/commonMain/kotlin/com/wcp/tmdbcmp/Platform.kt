package com.wcp.tmdbcmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
