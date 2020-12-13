package com.example.weatherforecast.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.timestampToMilliseconds(dateFormat: SimpleDateFormat): Long? {
    var result: Long? = null
    try {
        result = dateFormat.parse(this)?.time
    } catch (e: ParseException) {
        // do nothing
    }
    return result
}

fun tomorrowMills(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    return calendar.timeInMillis
}