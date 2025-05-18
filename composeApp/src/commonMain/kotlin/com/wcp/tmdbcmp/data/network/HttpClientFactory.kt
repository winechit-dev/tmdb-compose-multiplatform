package com.wcp.tmdbcmp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(): HttpClient =
        HttpClient {
            // Configure timeout
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
                connectTimeoutMillis = 60000
                socketTimeoutMillis = 60000
            }

            // Configure content negotiation with JSON
            install(ContentNegotiation) {
                val json =
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                json(json = json, contentType = ContentType.Any)
            }

            // Configure logging
            // ToDo: install this only in debug mode
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }

            // Configure default request
            defaultRequest {
                url("https://api.themoviedb.org/3/") // ToDo: Need to improve this
                headers {
                    append("Accept", "application/json")
                }
            }
        }
}
