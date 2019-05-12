package com.arctouch.codechallenge.feature.search

import clear
import com.arctouch.codechallenge.api.TmdbRepository
import com.arctouch.codechallenge.feature.common.CommonMovieViewModel
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single

class SearchViewModel(private val repository: TmdbRepository) : CommonMovieViewModel(repository) {

    private var searchString: String = ""

    fun search(text: String) {
        movies.clear()
        this.searchString = text
        load()
    }

    override fun createMovieSingle(page: Long): Single<MoviesResponse> {
        return repository.searchMovies(page, searchString)
    }

}