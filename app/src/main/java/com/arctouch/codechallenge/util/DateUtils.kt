package com.arctouch.codechallenge.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val defaultDateInputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun convertDateFromResponse(input: String?): String {
        val defaultDateOutputFormatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM)

        return try {
            val date = defaultDateInputFormatter.parse(input)
            defaultDateOutputFormatter.format(date)
        } catch (e: Exception) {
            ""
        }

    }

}