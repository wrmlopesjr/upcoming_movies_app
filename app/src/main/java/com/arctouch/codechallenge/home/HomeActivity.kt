package com.arctouch.codechallenge.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.NetworkState
import com.arctouch.codechallenge.base.components.LoadPageScrollListener
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), LoadPageScrollListener.LoadPageScrollLoadMoreListener  {
    private val viewModel by viewModel<HomeViewModel>()

    private val adapter = HomeAdapter()

    private val loadPageScrollListener = LoadPageScrollListener(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        viewModel.movies.observe(this, moviesObserver)
        viewModel.networkState.observe(this, networkStateObserver)

        recyclerView.addOnScrollListener(loadPageScrollListener)
        recyclerView.adapter = adapter
    }

    private val moviesObserver = Observer<MutableList<Movie>> { movies ->
        adapter.movies = movies
        adapter.notifyDataSetChanged()
    }

    private val networkStateObserver = Observer<NetworkState> { networkState ->
        when (networkState) {
            NetworkState.RUNNING -> progressBar.visibility = View.VISIBLE
            NetworkState.SUCCESS -> progressBar.visibility = View.GONE
            else -> progressBar.visibility = View.GONE
        }
    }

    override fun onLoadMore(currentPage: Long, totalItemCount: Long, recyclerView: RecyclerView) {
        viewModel.load(currentPage)
    }
}
