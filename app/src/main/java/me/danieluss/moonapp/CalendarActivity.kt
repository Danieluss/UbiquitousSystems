package me.danieluss.moonapp

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import me.danieluss.moonapp.algorithm.PhaseService
import java.util.*
import java.util.Calendar.YEAR


class CalendarActivity : AppCompatActivity() {

    var year: Int = Calendar.getInstance().get(YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        plusView.setOnClickListener {
            year++
            updateView()
        }
        minusView.setOnClickListener {
            if (year > 1971) {
                year--
                updateView()
            }
        }

        editNumber.isEnabled = false
        updateView()
    }

    private fun updateView() {
        editNumber.setText("$year")
        var phases = PhaseService.getPhases(year)

        val colors = listOf("Red", "Green", "Blue", "Yellow", "Black", "Crimson", "Orange")
        phaseListView.adapter = ArrayAdapter<String>(this, R.layout.listview_item, phases)
    }

}
