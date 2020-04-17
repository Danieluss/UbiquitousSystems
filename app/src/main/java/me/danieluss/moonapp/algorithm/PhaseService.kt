package me.danieluss.moonapp.algorithm

import me.danieluss.moonapp.Config
import me.danieluss.moonapp.Config.format
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

object PhaseService {

    var strategy: PhaseStrategy = CachedStrategy(TrigStrategy())

    fun getLastPhaseDate(): Calendar {
        var calendar = Utils.now()
        var strategyVal: Int
        do {
            // do not consider current date
            calendar.add(DATE, -1)
            strategyVal = strategy(calendar)
        } while (strategyVal != 0 && strategyVal != 15)
        return calendar
    }

    fun getNextPhaseDate(): Calendar {
        var calendar = Utils.now()
        var strategyVal: Int = -1
        while (strategyVal != 0 && strategyVal != 15) {
            strategyVal = strategy(calendar)
            calendar.add(DATE, 1)
        }
        return calendar
    }

    fun getPhases(year: Int = Calendar.getInstance().get(YEAR)): MutableList<String> {
        val phaseList: MutableList<String> = ArrayList()
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, 0, 1, 0, 0, 0)
        do {
            val phase: Int = strategy(calendar)
            if (phase == 15) {
                phaseList.add("${format.format(calendar.time)} - full")
            } else if (phase == 0) {
                phaseList.add("${format.format(calendar.time)} - new")
            }
            calendar.add(DATE, 1)
        } while (!(calendar.get(MONTH) == 0 && calendar.get(DATE) == 1))
        return phaseList
    }

    fun getPhase(calendar: Calendar): Int {
        return strategy(calendar)
    }

}