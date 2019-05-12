package com.arctouch.codechallenge.features.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.MOVIE
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.NetworkState
import com.arctouch.codechallenge.base.components.LoadPageScrollListener
import com.arctouch.codechallenge.features.detail.DetailActivity
import com.arctouch.codechallenge.features.home.adapter.HomeAdapter
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.view.HideStatusBarUtils
import kotlinx.android.synthetic.main.home_activity.*
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity(), LoadPageScrollListener.LoadPageScrollLoadMoreListener, HomeAdapter.HomeAdapterItemListener {

    private val viewModel by inject<HomeViewModel>()

    private val adapter = HomeAdapter(this)

    private val loadPageScrollListener = LoadPageScrollListener(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        HideStatusBarUtils.hide(this)

        viewModel.movies.observe(this, moviesObserver)
        viewModel.networkState.observe(this, networkStateObserver)

        recyclerView.addOnScrollListener(loadPageScrollListener)
        recyclerView.adapter = adapter
    }

    private val moviesObserver = Observer<MutableList<Movie>> { movies ->
        if (movies.isEmpty()) return@Observer
        adapter.movies = movies
        adapter.notifyDataSetChanged()
    }

    private val networkStateObserver = Observer<NetworkState> { networkState ->
        if (networkState == null) return@Observer
        when (networkState) {
            NetworkState.RUNNING -> progressBar.visibility = View.VISIBLE
            NetworkState.SUCCESS -> progressBar.visibility = View.GONE
            NetworkState.EMPTY -> {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            }
            NetworkState.ERROR -> {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoadMore(currentPage: Long, totalItemCount: Long, recyclerView: RecyclerView) {
        viewModel.load(currentPage)
    }

    override fun onClick(movie: Movie, view: View) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(MOVIE, movie)

        val poster = view.posterImageView

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, poster, "poster")
        startActivity(intent, options.toBundle())
    }
}
