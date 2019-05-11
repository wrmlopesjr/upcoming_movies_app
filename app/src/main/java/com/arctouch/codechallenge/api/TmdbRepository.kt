package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.API_KEY
import com.arctouch.codechallenge.DEFAULT_LANGUAGE
import com.arctouch.codechallenge.base.ApiService
import com.arctouch.codechallenge.base.BaseRepository
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Single

class TmdbRepository(apiService: ApiService) : BaseRepository<TmdbApi>(apiService) {

    fun genres(): Single<GenreResponse> {
        return schedule(getEndpoint().genres(API_KEY, DEFAULT_LANGUAGE))
    }

    fun upcomingMovies(page: Long): Single<UpcomingMoviesResponse> {
        return schedule(getEndpoint().upcomingMovies(API_KEY, DEFAULT_LANGUAGE, page, null))
    }

    fun movie(id: Long): Single<Movie> {
        return schedule(getEndpoint().movie(id, API_KEY, DEFAULT_LANGUAGE))
    }

}