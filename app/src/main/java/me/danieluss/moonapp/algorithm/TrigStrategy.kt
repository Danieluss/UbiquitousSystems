package me.danieluss.moonapp.algorithm

import me.danieluss.moonapp.algorithm.Utils.julday
import java.util.*
import java.util.Calendar.*

class TrigStrategy : PhaseStrategy {

    override fun compute(calendar: Calendar): Int {
        val year = calendar.get(YEAR)
        val month = calendar.get(MONTH) + 1
        val day = calendar.get(DAY_OF_MONTH) - 1
        val n = Math.floor(12.37 * (year - 1900 + ((1.0 * month - 0.5) / 12.0)))
        val RAD = 3.14159265 / 180.0
        val t = n / 1236.85
        val t2 = t * t
        val aS = 359.2242 + 29.105356 * n
        val am = 306.0253 + 385.816918 * n + 0.010730 * t2
        var xtra = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2
        xtra += (0.1734 - 3.93e-4 * t) * Math.sin(RAD * aS) - 0.4068 * Math.sin(RAD * am)
        val i = if (xtra > 0.0) (Math.floor(xtra)) else (Math.ceil(xtra - 1.0))
        val j1 = julday(year, month, day)
        val jd = ((2415020 + 28 * n) + i).toInt()
        return (j1 - jd + 30) % 30
    }

}