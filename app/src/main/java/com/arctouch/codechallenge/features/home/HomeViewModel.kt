package com.arctouch.codechallenge.features.home

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
import empty
import error
import io.reactivex.rxkotlin.Singles
import running
import success

class HomeViewModel(private val repository: TmdbRepository) : BaseViewModel() {

    val movies = MutableLiveData<MutableList<Movie>>()
    val networkState = MutableLiveData<NetworkState>()

    init {
        reload()
    }

    private fun reload() {
        movies.clear()
        load()
    }

    fun load(page: Long = 1L) {
        if(page==1L) networkState.running()

        val upcomingMoviesSingle = repository.upcomingMovies(page)

        if (Cache.genres.isEmpty()) {
            val genresSingle = repository.genres()

            addDisposable(Singles.zip(genresSingle, upcomingMoviesSingle)
                    .subscribe({ onLoadSuccess(it, page) }, { onLoadError() }))

        } else {
            addDisposable(upcomingMoviesSingle.subscribe({ onLoadSuccess(it, page) }, { onLoadError() }))
        }
    }

    private fun onLoadSuccess(it: Pair<GenreResponse, UpcomingMoviesResponse>, page: Long) {
        Cache.cacheGenres(it.first.genres)

        onLoadSuccess(it.second, page)
    }

    private fun onLoadSuccess(it: UpcomingMoviesResponse, page: Long) {
        val moviesWithGenres = it.results.map { movie ->
            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
        }

        if(page==1L && moviesWithGenres.isEmpty()){
            networkState.empty()
            return
        }

        movies.addListValues(moviesWithGenres)

        networkState.success()
    }

    private fun onLoadError() {
        networkState.error()
    }
}