package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.API_KEY
import org.junit.Assert.assertEquals
import org.junit.Test

private val EXPECTED_POSTER_URL = "https://image.tmdb.org/t/p/w154/poster?api_key=$API_KEY"
private val EXPECTED_BACKDROP_URL = "https://image.tmdb.org/t/p/w780/backdrop?api_key=$API_KEY"

class MovieImageUrlBuilderTest {

    @Test
    fun testUrlBuilder() {
        val posterBuiltURL = MovieImageUrlBuilder.buildPosterUrl("/poster")
        val backdropBuiltURL = MovieImageUrlBuilder.buildBackdropUrl("/backdrop")

        assertEquals(EXPECTED_POSTER_URL, posterBuiltURL)
        assertEquals(EXPECTED_BACKDROP_URL, backdropBuiltURL)
    }


}