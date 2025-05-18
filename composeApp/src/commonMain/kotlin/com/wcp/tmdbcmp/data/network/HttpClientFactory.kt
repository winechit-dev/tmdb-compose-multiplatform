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
                // ToDo: Need to improve token management
                val tk =
                    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MzE2ODRmMmY1N2I0YTNmMGQ1MjBhZmFlMGVlNmE0ZiIsIm5iZiI6MTcyOTA2NTcwMS41OTM4MDcsInN1YiI6IjVkYzI1Njg3OWQ4OTM5MDAxODMzMTExZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lm5zq9fpr96S6Te7e4nuTaOxtBjlShc2Z_3VYtPmy3U"
                        .prefixBearer()
                url("https://api.themoviedb.org/3/") // ToDo: Need to improve this
                headers {
                    append("Content-Type", "application/json-patch+json")
                    append("Authorization", tk)
                    append("Accept", "text/plain")
                }
            }
        }
}

private fun String.prefixBearer() = "Bearer $this"
