package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.API_KEY

private val POSTER_URL = "https://image.tmdb.org/t/p/w154"
private val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

object MovieImageUrlBuilder {

    fun buildPosterUrl(posterPath: String): String {
        return "$POSTER_URL$posterPath?api_key=$API_KEY"
    }

    fun buildBackdropUrl(backdropPath: String): String {
        return "$BACKDROP_URL$backdropPath?api_key=$API_KEY"
    }
}
