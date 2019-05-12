package com.arctouch.codechallenge.feature.common

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.MOVIE
import com.arctouch.codechallenge.base.NetworkState
import com.arctouch.codechallenge.base.components.LoadPageScrollListener
import com.arctouch.codechallenge.feature.detail.DetailActivity
import com.arctouch.codechallenge.feature.common.adapter.MovieAdapter
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.view.HideStatusBarUtils
import kotlinx.android.synthetic.main.home_activity.*
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlinx.android.synthetic.main.progress_bar.*

abstract class CommonMovieActivity : AppCompatActivity(), LoadPageScrollListener.LoadPageScrollLoadMoreListener, MovieAdapter.HomeAdapterItemListener {

    private val adapter = MovieAdapter(this)

    private val loadPageScrollListener = LoadPageScrollListener(this)

    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResource)

        HideStatusBarUtils.hide(this)

        moviesList.addOnScrollListener(loadPageScrollListener)
        moviesList.adapter = adapter
    }

    protected val moviesObserver = Observer<MutableList<Movie>> { movies ->
        if (movies.isEmpty()) return@Observer
        adapter.movies = movies
        adapter.notifyDataSetChanged()
    }

    protected val networkStateObserver = Observer<NetworkState> { networkState ->
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

    override fun onClick(movie: Movie, view: View) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(MOVIE, movie)

        val poster = view.posterImageView

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, poster, "poster")
        startActivity(intent, options.toBundle())
    }

}