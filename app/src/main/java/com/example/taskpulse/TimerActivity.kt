package com.example.taskpulse

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TimerActivity : AppCompatActivity() {

    private var startTime: Long = 0
    private var timeInMillis: Long = 0
    private var handler: Handler? = null
    private var isRunning = false
    private var updateTimer = object : Runnable {
        override fun run() {
            val elapsedTime = SystemClock.elapsedRealtime() - startTime
            val seconds = (elapsedTime / 1000) % 60
            val minutes = (elapsedTime / (1000 * 60)) % 60
            val hours = (elapsedTime / (1000 * 60 * 60)) % 24
            findViewById<TextView>(R.id.timerTextView).text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            handler?.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val startButton: Button = findViewById(R.id.getstart)
        val stopButton: Button = findViewById(R.id.stopButton)
        val resetButton: Button = findViewById(R.id.resetButton)

        startButton.setOnClickListener {
            if (!isRunning) {
                startTime = SystemClock.elapsedRealtime()
                handler = Handler()
                handler?.post(updateTimer)
                isRunning = true
            }
        }

        stopButton.setOnClickListener {
            if (isRunning) {
                handler?.removeCallbacks(updateTimer)
                isRunning = false
            }
        }

        resetButton.setOnClickListener {
            if (isRunning) {
                handler?.removeCallbacks(updateTimer)
                isRunning = false
            }
            findViewById<TextView>(R.id.timerTextView).text = "00:00:00"
        }


        val navTasks: ImageButton = findViewById(R.id.nav_tasks)
        val navTimer: ImageButton = findViewById(R.id.nav_timer)
        val navReminders: ImageButton = findViewById(R.id.nav_reminders)
        val navProfile: ImageButton = findViewById(R.id.nav_profile)

        navTasks.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        navTimer.setOnClickListener {
            // Already in TimerActivity
        }

        navReminders.setOnClickListener {
            startActivity(Intent(this, ReminderActivity::class.java))
        }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
