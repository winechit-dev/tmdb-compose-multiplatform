package com.wcp.tmdbcmp.data.network

import com.wcp.tmdbcmp.data.network.model.CreditsResponse
import com.wcp.tmdbcmp.data.network.model.GenresResponse
import com.wcp.tmdbcmp.data.network.model.MovieDetailsResponse
import com.wcp.tmdbcmp.data.network.model.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class MovieApi(
    private val httpClient: HttpClient,
) {
    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val LANGUAGE = "en-US"
        const val API_KEY: String = "431684f2f57b4a3f0d520afae0ee6a4f" // Replace with your actual API key
    }

    suspend fun getTrendingTodayMovies(
        page: Int = STARTING_PAGE_INDEX,
        apiKey: String = API_KEY,
        language: String = LANGUAGE,
    ): MoviesResponse =
        httpClient
            .get {
                url("trending/movie/day")
                parameter("page", page)
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body()

    suspend fun getPopularMovies(
        page: Int = STARTING_PAGE_INDEX,
        apiKey: String = API_KEY,
        language: String = LANGUAGE,
    ): MoviesResponse =
        httpClient
            .get {
                url("movie/popular")
                parameter("page", page)
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body()

    suspend fun getUpcomingMovies(
        page: Int = STARTING_PAGE_INDEX,
        apiKey: String = API_KEY,
        language: String = LANGUAGE,
    ): MoviesResponse =
        httpClient
            .get {
                url("movie/upcoming")
                parameter("page", page)
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body()

    suspend fun getTopRatedMovies(
        page: Int = STARTING_PAGE_INDEX,
        apiKey: String = API_KEY,
        language: String = LANGUAGE,
    ): MoviesResponse =
        httpClient
            .get {
                url("movie/top_rated")
                parameter("page", page)
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body()

    suspend fun getNowPlayingMovies(
        page: Int = STARTING_PAGE_INDEX,
        apiKey: String = API_KEY,
        language: String = LANGUAGE,
    ): MoviesResponse =
        httpClient
            .get {
                url("movie/now_playing")
                parameter("page", page)
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body()

    suspend fun getMovieGenres(
        apiKey: String = API_KEY,
        language: String = LANGUAGE,
    ): GenresResponse =
        httpClient
            .get {
                url("genre/movie/list")
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body()

    suspend fun getMovieDetails(
        movieId: Int,
        apiKey: String = API_KEY,
        language: String = LANGUAGE,
    ): MovieDetailsResponse =
        httpClient
            .get {
                url("movie/$movieId")
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body()

    suspend fun getCreditDetails(
        movieId: Int,
        apiKey: String,
    ): CreditsResponse =
        httpClient
            .get {
                url("movie/$movieId/credits")
                parameter("api_key", apiKey)
            }.body()

    suspend fun getRecommendations(
        movieId: Int,
        page: Int = STARTING_PAGE_INDEX,
        apiKey: String = API_KEY,
        language: String = LANGUAGE,
    ): MoviesResponse =
        httpClient
            .get {
                url("movie/$movieId/recommendations")
                parameter("page", page)
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body()

    suspend fun searchMovie(
        query: String,
        page: Int = STARTING_PAGE_INDEX,
        apiKey: String = API_KEY,
    ): MoviesResponse =
        httpClient
            .get {
                url("search/movie")
                parameter("query", query)
                parameter("page", page)
                parameter("api_key", apiKey)
            }.body()
}
