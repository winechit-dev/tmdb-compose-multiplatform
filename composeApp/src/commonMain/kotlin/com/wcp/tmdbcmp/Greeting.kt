package com.wcp.tmdbcmp

class Greeting {
    private val platform = getPlatform()

    fun greet(): String = "Hello, ${platform.name}!"
}
