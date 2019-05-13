package com.arctouch.codechallenge.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateUtilsTest {

    @Test
    fun testConvertDateFromResponseUS() {
        Locale.setDefault(Locale.US)

        assertEquals("Jan 2, 2019", DateUtils.convertDateFromResponse("2019-01-02"))
        assertEquals("May 25, 1987", DateUtils.convertDateFromResponse("1987-05-25"))
    }

    @Test
    fun testConvertDateFromResponseBR() {
        Locale.setDefault(Locale("pt", "BR"))

        assertEquals("02/01/2019", DateUtils.convertDateFromResponse("2019-01-02"))
        assertEquals("25/05/1987", DateUtils.convertDateFromResponse("1987-05-25"))
    }

    @Test
    fun testConvertDateFromResponseError() {
        assertEquals("", DateUtils.convertDateFromResponse(null))
        assertEquals("", DateUtils.convertDateFromResponse(""))
    }


}