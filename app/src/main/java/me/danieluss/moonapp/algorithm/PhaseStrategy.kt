package me.danieluss.moonapp.algorithm

import java.util.*

interface PhaseStrategy {

    operator fun invoke(calendar: Calendar): Int {
        return compute(calendar)
    }

    fun compute(calendar: Calendar): Int

}