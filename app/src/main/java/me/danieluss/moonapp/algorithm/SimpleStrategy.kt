package me.danieluss.moonapp.algorithm

import java.util.*
import kotlin.math.floor

class SimpleStrategy : PhaseStrategy {

    override fun compute(calendar: Calendar): Int {
        val lp = 2551443.0
        val newMoon = Calendar.getInstance()
        newMoon.set(1970, 0, 7, 20, 35, 0)
        val phase = ((calendar.time.time - newMoon.time.time)/1000.0) % lp
        return floor(phase /(24.0*3600.0)).toInt()
    }

}