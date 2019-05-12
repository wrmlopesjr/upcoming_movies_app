package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.API_KEY
import com.arctouch.codechallenge.DEFAULT_LANGUAGE
import com.arctouch.codechallenge.base.ApiService
import com.arctouch.codechallenge.base.BaseRepository
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Single
import java.util.*

class TmdbRepository(apiService: ApiService) : BaseRepository<TmdbApi>(apiService) {

    val locale = Locale.getDefault().language
    val region = Locale.getDefault().country

    fun genres(): Single<GenreResponse> {
        return schedule(getApi().genres(API_KEY, locale))
    }

    fun upcomingMovies(page: Long): Single<UpcomingMoviesResponse> {
        return schedule(getApi().upcomingMovies(API_KEY, locale, page, region))
    }

    fun movie(id: Long): Single<Movie> {
        return schedule(getApi().movie(id, API_KEY, locale))
    }

}