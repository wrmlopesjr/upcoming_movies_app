package com.arctouch.codechallenge.feature.detail

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.arctouch.codechallenge.MOVIE
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.DateUtils
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.util.view.HideStatusBarUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.detail_activity.*
import kotlinx.android.synthetic.main.progress_bar.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        setupTransition()

        HideStatusBarUtils.hide(this)

        configureView((intent?.extras?.getSerializable(MOVIE) as Movie))
    }

    private fun configureView(movie: Movie) {
        progressBar.visibility = View.GONE

        backButton.setOnClickListener { onBackPressed() }

        titleTextView.text = movie.title
        genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
        releaseDateTextView.text = DateUtils.convertDateFromResponse(movie.releaseDate)

        if (movie.voteAverage > 0) {
            setEvaluationText(movie.voteAverage)
        }

        if (movie.overview.isNullOrBlank()) {
            overviewText.text = getString(R.string.overview_not_available)
        } else {
            overviewText.text = movie.overview
        }

        Glide.with(this)
                .load(movie.posterPath?.let { MovieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.ic_image_placeholder).fitCenter()
                        .transform(RoundedCorners(10)))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                })
                .into(posterImageView)

        Glide.with(this)
                .load(movie.posterPath?.let { MovieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().fitCenter().transform(BlurTransformation(25, 3)))
                .into(backgroundImageView)

        Glide.with(this)
                .load(movie.backdropPath?.let { MovieImageUrlBuilder.buildBackdropUrl(it) })
                .into(backdropImageView)
    }

    private fun setupTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            posterImageView.transitionName = "poster"
            posterImageView.elevation = resources.getDimension(R.dimen.default_elevation)
        }

        supportPostponeEnterTransition()
    }

    private fun setEvaluationText(voteAverage: Float) {
        evaluationTextView.text = getString(R.string.vote_average, voteAverage.toString())
        evaluationStar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }
}
