package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.API_KEY
import com.arctouch.codechallenge.DEFAULT_LANGUAGE
import com.arctouch.codechallenge.DEFAULT_REGION
import com.arctouch.codechallenge.base.ApiService
import com.arctouch.codechallenge.base.BaseRepository
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single
import java.util.*

class TmdbRepository(apiService: ApiService) : BaseRepository<TmdbApi>(apiService) {

    val locale = Locale.getDefault().language ?: DEFAULT_LANGUAGE
    val region = Locale.getDefault().country ?: DEFAULT_REGION

    fun genres(): Single<GenreResponse> {
        return schedule(getApi().genres(API_KEY, locale))
    }

    fun upcomingMovies(page: Long): Single<MoviesResponse> {
        return schedule(getApi().upcomingMovies(API_KEY, locale, page, region))
    }

    fun searchMovies(page: Long, query: String): Single<MoviesResponse> {
        return schedule(getApi().searchMovies(API_KEY, locale, page, region, query))
    }

}