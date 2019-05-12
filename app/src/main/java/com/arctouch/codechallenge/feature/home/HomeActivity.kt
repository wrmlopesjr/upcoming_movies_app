package com.arctouch.codechallenge.feature.home

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.feature.common.CommonMovieActivity
import com.arctouch.codechallenge.feature.search.SearchActivity
import kotlinx.android.synthetic.main.error_state.*
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.android.ext.android.inject

class HomeActivity : CommonMovieActivity() {

    private val viewModel by inject<HomeViewModel>()

    override val layoutResource = R.layout.home_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObservers()

        setupView()
    }

    private fun setupView() {
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent, null)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        error_state_retry.setOnClickListener {
            viewModel.load()
        }
    }

    private fun setupObservers() {
        viewModel.movies.observe(this, moviesObserver)
        viewModel.networkState.observe(this, networkStateObserver)
    }

    override fun onLoadMore(currentPage: Long, totalItemCount: Long, recyclerView: RecyclerView) {
        viewModel.load(currentPage)
    }
}
