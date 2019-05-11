package com.arctouch.codechallenge.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.NetworkState
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        viewModel.movies.observe(this, moviesObserver)
        viewModel.networkState.observe(this, networkStateObserver)

        viewModel.load()
    }

    private val moviesObserver = Observer<MutableList<Movie>> { movies ->
        recyclerView.adapter = HomeAdapter(movies)
    }

    private val networkStateObserver = Observer<NetworkState> { networkState ->
        when (networkState) {
            NetworkState.RUNNING -> progressBar.visibility = View.VISIBLE
            NetworkState.SUCCESS -> progressBar.visibility = View.GONE
            else -> progressBar.visibility = View.GONE
        }
    }
}
