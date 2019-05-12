package com.arctouch.codechallenge.feature.home

import clear
import com.arctouch.codechallenge.api.TmdbRepository
import com.arctouch.codechallenge.feature.common.CommonMovieViewModel
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single

class HomeViewModel(private val repository: TmdbRepository) : CommonMovieViewModel(repository) {

    init {
        reload()
    }

    private fun reload() {
        movies.clear()
        load()
    }

    override fun createMovieSingle(page: Long): Single<MoviesResponse> {
        return repository.upcomingMovies(page)
    }
}