package me.danieluss.moonapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import me.danieluss.moonapp.Config.format
import me.danieluss.moonapp.algorithm.*
import java.util.*


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    var south: Boolean = false
    var algorithm: String = "trig2"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSharedPreferences()

        calendarButton.setOnClickListener(::showCalendar)

        updateView()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_settings) {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        updateView()
    }

    fun updateView() {
        PhaseService.strategy = when(sharedPreferences.getString("algorithm", "trig2")) {
            "simple" -> CachedStrategy(SimpleStrategy())
            "conway" -> CachedStrategy(ConwayStrategy())
            "trig" -> CachedStrategy(TrigStrategy())
            "trig2" -> CachedStrategy(Trig2Strategy())
            else -> CachedStrategy(Trig2Strategy())
        }
        algorithm = sharedPreferences.getString("algorithm", "trig2")!!
        south = sharedPreferences.getBoolean("south_hemisphere", false)
        updatePhase()
    }

    private fun updatePhase() {
        val currentPhase = PhaseService.getPhase(Utils.now())
        val rightDate: Calendar = PhaseService.getNextPhaseDate()
        if (currentPhase <= 15) {
            setProgressImages(rightDate, R.drawable.new_moon, R.drawable.full_moon)
            updateProgress((currentPhase / 15.0 * 100).toInt())
        } else {
            setProgressImages(rightDate, R.drawable.full_moon, R.drawable.new_moon)
            updateProgress(((30 - currentPhase) / 15.0 * 100).toInt())
        }
        moonImageView.setImageResource(
            resources.getIdentifier(
                phaseIdentifier(currentPhase),
                "drawable",
                packageName
            )
        )
    }

    private fun phaseIdentifier(currentPhase: Int): String {
        return if (south) "s_$currentPhase" else "n_${(currentPhase - 1) % 30}"
    }

    private fun setProgressImages(rightDate: Calendar, leftImage: Int, rightImage: Int) {
        rightDateView.text = format.format(rightDate.time)
        rightImageView.setImageResource(rightImage)
        val leftDate: Calendar = PhaseService.getLastPhaseDate()
        leftDateView.text = format.format(leftDate.time)
        leftImageView.setImageResource(leftImage)
    }

    private fun updateProgress(progress: Int) {
        progressBar.progress = progress
        todayView.text =
            getString(R.string.progress_date).format(format.format(Utils.now().time), progress, algorithm)
    }

    private fun showCalendar(v: View?) {
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }

    private fun setupSharedPreferences() {
        sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

}
