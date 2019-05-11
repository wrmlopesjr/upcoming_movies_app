package com.arctouch.codechallenge.home

import addListValues
import androidx.lifecycle.MutableLiveData
import clear
import com.arctouch.codechallenge.api.TmdbRepository
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.base.NetworkState
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import error
import io.reactivex.rxkotlin.Singles
import running
import success

class HomeViewModel(private val repository: TmdbRepository) : BaseViewModel() {

    val movies = MutableLiveData<MutableList<Movie>>()
    val networkState = MutableLiveData<NetworkState>()

    init{
        reload()
    }

    private fun reload() {
        movies.clear()
        load()
    }

    fun load(page: Long = 1) {
        networkState.running()

        val upcomingMoviesSingle = repository.upcomingMovies(page)

        if (Cache.genres.isEmpty()) {
            val genresSingle = repository.genres()

            addDisposable(Singles.zip(genresSingle, upcomingMoviesSingle)
                    .subscribe({ onLoadSuccess(it) }, { onLoadError() }))

        } else {
            addDisposable(upcomingMoviesSingle.subscribe({ onLoadSuccess(it) }, { onLoadError() }))
        }
    }

    private fun onLoadSuccess(it: Pair<GenreResponse, UpcomingMoviesResponse>) {
        Cache.cacheGenres(it.first.genres)

        onLoadSuccess(it.second)
    }

    private fun onLoadSuccess(it: UpcomingMoviesResponse) {
        val moviesWithGenres = it.results.map { movie ->
            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
        }

        movies.addListValues(moviesWithGenres)

        networkState.success()
    }

    private fun onLoadError() {
        networkState.error()
    }
}