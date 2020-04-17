package me.danieluss.moonapp.algorithm

import java.util.*

class ConwayStrategy : PhaseStrategy {

    override fun compute(calendar: Calendar): Int {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH) - 1

        var r = (year % 100).toDouble()
        r %= 19
        if (r > 9) {
            r -= 19
        }
        r = r * 11 % 30 + month + day
        if (month < 3) {
            r += 2
        }
        r -= if (year < 2000) 4.0 else 8.3
        r = Math.floor(r + 0.5) % 30
        return (if (r < 0) r + 30 else r).toInt()
    }

}