package com.arctouch.codechallenge.feature

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseInstrumentedTest
import com.arctouch.codechallenge.feature.detail.DetailActivity
import com.arctouch.codechallenge.feature.search.SearchActivity
import com.arctouch.codechallenge.model.MoviesResponse
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchActivityInstrumentedTest : BaseInstrumentedTest() {

    @get:Rule
    val activityRule = ActivityTestRule(SearchActivity::class.java, false, false)


    private fun launchActivity() {
        val intent = Intent()
        activityRule.launchActivity(intent)
        Intents.init()
    }

    @Test
    fun testSearch() {

        launchActivity()

        onView(withId(R.id.searchText)).perform(typeText("Teste"))
        onView(withId(R.id.executeSearch)).perform(click())

        waitLoading()

        onView(withId(R.id.moviesList)).check(matches(isDisplayed()))
        onView(ViewMatchers.withChild(ViewMatchers.withText(containsString("Detective Pikachu")))).check(matches(isDisplayed()))

        //click on first item
        onView(ViewMatchers.withChild(ViewMatchers.withText("Extremely Wicked, Shockingly Evil and Vile"))).perform(ViewActions.click())

        //check Detail activity is open
        Intents.intended(IntentMatchers.hasComponent(DetailActivity::class.java.name))
    }

    @After
    fun clearTest() {
        Intents.release()
    }

}