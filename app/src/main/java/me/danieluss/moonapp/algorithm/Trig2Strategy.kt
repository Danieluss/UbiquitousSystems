package me.danieluss.moonapp.algorithm

import me.danieluss.moonapp.algorithm.Utils.fraction
import me.danieluss.moonapp.algorithm.Utils.julday
import java.util.*
import kotlin.math.floor

class Trig2Strategy : PhaseStrategy {
    override fun compute(calendar: Calendar): Int {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH) - 1
        val thisJD = julday(year, month, day)
        val degToRad = 3.14159265 / 180.0
        val K0 = Math.floor((year - 1900) * 12.3685)
        val T = (year - 1899.5) / 100.0
        val T2 = T * T
        val T3 = T * T * T
        val J0 = 2415020 + 29 * K0;
        val F0 =
            0.0001178 * T2 - 0.000000155 * T3 + (0.75933 + 0.53058868 * K0) - (0.000837 * T + 0.000335 * T2);
        val M0 = 360 * (fraction(K0 * 0.08084821133)) + 359.2242 - 0.0000333 * T2 - 0.00000347 * T3;
        val M1 = 360 * (fraction(K0 * 0.07171366128)) + 306.0253 + 0.0107306 * T2 + 0.00001236 * T3;
        val B1 =
            360 * (fraction(K0 * 0.08519585128)) + 21.2964 - (0.0016528 * T2) - (0.00000239 * T3);
        var phase = 0.0;
        var jday = 0.0;
        var oldJ = 0.0
        while (jday < thisJD) {
            var F = F0 + 1.530588 * phase;
            var M5 = (M0 + phase * 29.10535608) * degToRad;
            var M6 = (M1 + phase * 385.81691806) * degToRad;
            var B6 = (B1 + phase * 390.67050646) * degToRad;
            F -= 0.4068 * Math.sin(M6) + (0.1734 - 0.000393 * T) * Math.sin(M5);
            F += 0.0161 * Math.sin(2 * M6) + 0.0104 * Math.sin(2 * B6);
            F -= 0.0074 * Math.sin(M5 - M6) - 0.0051 * Math.sin(M5 + M6);
            F += 0.0021 * Math.sin(2 * M5) + 0.0010 * Math.sin(2 * B6 - M6);
            F += 0.5 / 1440;
            oldJ = jday;
            jday = J0 + 28 * phase + Math.floor(F);
            phase++;
        }
        return (thisJD - oldJ).toInt() % 30;
    }

}