package com.arctouch.codechallenge.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val defaultDateInputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val defaultDateOutputFormatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)!!

    fun convertDateFromResponse(input: String?): String {
        return try {
            val date = defaultDateInputFormatter.parse(input)
            defaultDateOutputFormatter.format(date)
        } catch (e: Exception) {
            ""
        }

    }

}