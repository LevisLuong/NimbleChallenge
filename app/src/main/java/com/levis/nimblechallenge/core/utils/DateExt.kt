package com.levis.nimblechallenge.core.utils

import com.levis.nimblechallenge.core.common.DATE_TIME_PATTERN_DISPLAY
import com.levis.nimblechallenge.core.common.DATE_TIME_PATTERN_RESPONSE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Date.format(pattern: String = DATE_TIME_PATTERN_DISPLAY, timeZone: TimeZone? = null): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    timeZone?.let { formatter.timeZone = timeZone }
    return formatter.format(this)
}

fun String.parse(pattern: String = DATE_TIME_PATTERN_RESPONSE, timeZone: TimeZone? = null): Date? {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    timeZone?.let { formatter.timeZone = timeZone }
    return try {
        formatter.parse(this)
    } catch (e: Exception) {
        null
    }
}
