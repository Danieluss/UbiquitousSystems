package me.danieluss.moonapp

import me.danieluss.moonapp.algorithm.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar.*

// approximate strategy sanity tests
class StrategyTest() {

    fun strategyRangeTest(strategy: PhaseStrategy) {
        val calendar = Utils.now()
        val strategyValue = strategy(calendar)
        println("${calendar.get(YEAR)} ${calendar.get(MONTH)} ${calendar.get(DAY_OF_MONTH)} ~ ${strategy(calendar)}")
        calendar.add(DATE, -strategyValue + 1);
        var strategyVal = strategy(calendar)
        println("${calendar.get(YEAR)} ${calendar.get(MONTH)} ${calendar.get(DAY_OF_MONTH)} ~ ${strategyVal}")
        assertTrue(strategyVal in listOf(0, 1, 29))
        calendar.add(DATE, 29);
        strategyVal = strategy(calendar)
        println("${calendar.get(YEAR)} ${calendar.get(MONTH)} ${calendar.get(DAY_OF_MONTH)} ~ ${strategyVal}")
        assertTrue(strategyVal in listOf(0, 1, 29))
    }

    @Test
    fun trigStrategyTest() {
        strategyRangeTest(Trig2Strategy())
    }

    @Test
    fun trig2StrategyTest() {
        strategyRangeTest(Trig2Strategy())
    }

    @Test
    fun conwayStrategyTest() {
        strategyRangeTest(ConwayStrategy())
    }

}
