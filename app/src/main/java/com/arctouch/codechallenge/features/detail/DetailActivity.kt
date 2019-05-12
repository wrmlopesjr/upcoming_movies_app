package com.arctouch.codechallenge.features.detail

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.arctouch.codechallenge.MOVIE
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
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
import kotlinx.android.synthetic.main.detail_activity.*
import kotlinx.android.synthetic.main.progress_bar.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            posterImageView.transitionName = "poster"
            posterImageView.elevation = resources.getDimension(R.dimen.default_elevation)
        }

        HideStatusBarUtils.hide(this)

        supportPostponeEnterTransition()

        (intent?.extras?.getSerializable(MOVIE) as Movie).let { movie ->
            titleTextView.text = movie.title
            genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
            releaseDateTextView.text = movie.releaseDate

            if(movie.overview.isNullOrBlank()){
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
                    .load(movie.backdropPath?.let { MovieImageUrlBuilder.buildBackdropUrl(it) })
                    .into(backdropImageView)
        }

        progressBar.visibility = View.GONE

    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }
}
