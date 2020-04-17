package me.danieluss.moonapp.algorithm

import java.util.*
import kotlin.collections.HashMap

class CachedStrategy(var strategy: PhaseStrategy) : PhaseStrategy {

    var computedMap: MutableMap<Calendar, Int> = HashMap()

    override fun compute(calendar: Calendar): Int {
        val result = computedMap[calendar] ?: strategy.compute(calendar)
        computedMap[calendar] = result
        return result
    }

}