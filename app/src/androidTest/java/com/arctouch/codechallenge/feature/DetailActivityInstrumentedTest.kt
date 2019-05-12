package com.arctouch.codechallenge.feature

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.arctouch.codechallenge.MOVIE
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseInstrumentedTest
import com.arctouch.codechallenge.features.detail.DetailActivity
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityInstrumentedTest : BaseInstrumentedTest() {

    @get:Rule
    val activityRule = ActivityTestRule(DetailActivity::class.java, false, false)

    val genres = listOf(Genre(1, "Genre 1"), Genre(2, "Genre 2"))
    val overview = "Test overview about this movie"
    val title = "Movie Test"
    val releaseDate = "2019-05-24"
    val movie = Movie(1, title, overview, genres, listOf(), "/poster", "/backdrop", releaseDate)


    private fun launchActivity() {
        val intent = Intent()
        intent.putExtra(MOVIE, movie)
        activityRule.launchActivity(intent)
        Intents.init()
    }

    @Test
    fun testDetail() {

        launchActivity()

        waitLoading()

        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTextView)).check(matches(withText(title)))

        onView(withId(R.id.genresTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.genresTextView)).check(matches(withText(genres.joinToString(separator = ", "){ it.name })))

        onView(withId(R.id.releaseDateTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDateTextView)).check(matches(withText(releaseDate)))

        onView(withId(R.id.overviewText)).check(matches(isDisplayed()))
        onView(withId(R.id.overviewText)).check(matches(withText(overview)))

    }

    @After
    fun clearTest() {
        Intents.release()
    }

}