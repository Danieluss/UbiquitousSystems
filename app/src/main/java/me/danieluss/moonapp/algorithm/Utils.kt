package me.danieluss.moonapp.algorithm

import java.util.*
import java.util.Calendar.*
import kotlin.math.floor

object Utils {

    fun now(truncateAtDay: Boolean = true): Calendar {
        val calendar = getInstance()
        if (truncateAtDay) {
            calendar.set(
                calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH),
                0,
                0,
                0
            )
        }
        return calendar
    }

    fun given(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0): Calendar {
        val calendar = getInstance()
        calendar.set(year, month, day, hour, minute, second)
        return calendar
    }

    fun julday(year: Int, month: Int, day: Int): Int {
        var jy = year
        var jm = month + 1
        if (month <= 2) {
            jy--
            jm += 12
        }
        var jul = Math.floor(365.25 * jy) + Math.floor(30.6001 * jm) + day + 1720995
        if (day + 31 * (month + 12 * year) >= (15 + 31 * (10 + 12 * 1582))) {
            val ja = Math.floor(0.01 * jy);
            jul = jul + 2 - ja + Math.floor(0.25 * ja);
        }
        return jul.toInt();
    }

    fun fraction(x: Double): Double {
        return x - floor(x)
    }

}