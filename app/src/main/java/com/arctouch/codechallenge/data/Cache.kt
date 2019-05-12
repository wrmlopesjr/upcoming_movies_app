package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.model.Genre

object Cache {

    var genres = listOf<Genre>()
        private set

    fun cacheGenres(genres: List<Genre>) {
        this.genres = genres
    }

    fun clear() {
        genres = listOf()
    }
}
