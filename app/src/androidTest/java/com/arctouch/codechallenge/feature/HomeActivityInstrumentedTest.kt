package com.arctouch.codechallenge.feature

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
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
import com.arctouch.codechallenge.features.detail.DetailActivity
import com.arctouch.codechallenge.features.home.HomeActivity
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityInstrumentedTest : BaseInstrumentedTest() {

    val emptyResponse = UpcomingMoviesResponse(3, ArrayList(), 2, 38)

    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java, false, false)


    private fun launchActivity() {
        val intent = Intent()
        activityRule.launchActivity(intent)
        Intents.init()
    }

    @Test
    fun testHome() {

        launchActivity()

        waitLoading()

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(ViewMatchers.withChild(ViewMatchers.withText("Pokémon Detective Pikachu"))).check(matches(isDisplayed()))

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