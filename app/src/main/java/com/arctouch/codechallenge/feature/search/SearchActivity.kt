package com.arctouch.codechallenge.feature.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.SEARCH_STRING
import com.arctouch.codechallenge.feature.common.CommonMovieActivity
import kotlinx.android.synthetic.main.progress_bar.*
import kotlinx.android.synthetic.main.search_activity.*
import org.koin.android.ext.android.inject


class SearchActivity : CommonMovieActivity() {

    private val viewModel by inject<SearchViewModel>()

    override val layoutResource = R.layout.search_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObservers()

        setupView()

        restoreData(savedInstanceState)
    }

    private fun restoreData(savedInstanceState: Bundle?) {
        savedInstanceState?.getString(SEARCH_STRING)?.let { searchString ->
            searchText.setText(searchString)
            viewModel.search(searchString)
        }
    }

    private fun setupView() {
        executeSearch.setOnClickListener {
            viewModel.search(searchText.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(executeSearch.windowToken, 0)
        }

        progressBar.visibility = View.GONE

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObservers() {
        viewModel.movies.observe(this, moviesObserver)
        viewModel.networkState.observe(this, networkStateObserver)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if(!searchText.text.isNullOrBlank()){
            outState?.putString(SEARCH_STRING, searchText.text.toString())
        }

        super.onSaveInstanceState(outState)
    }

    override fun onLoadMore(currentPage: Long, totalItemCount: Long, recyclerView: RecyclerView) {
        viewModel.load(currentPage)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
