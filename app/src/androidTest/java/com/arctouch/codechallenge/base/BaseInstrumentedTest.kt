package com.arctouch.codechallenge.base

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.arctouch.codechallenge.R

open class BaseInstrumentedTest : BaseTest(true) {

    internal fun waitLoading() {
        Thread.sleep(100)
        while (isLoadingVisible()) {
            Thread.sleep(200)
        }

    }

    private fun isLoadingVisible(): Boolean {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.progressBar))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            return true
        } catch (ignored: Throwable) {
        }
        return false
    }

}