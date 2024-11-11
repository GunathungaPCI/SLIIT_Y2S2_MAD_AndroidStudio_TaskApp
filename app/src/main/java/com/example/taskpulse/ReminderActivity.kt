package com.example.taskpulse

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ReminderActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        val reminderEditText: EditText = findViewById(R.id.reminderEditText)
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val setReminderButton: Button = findViewById(R.id.setReminderButton)


        val navTasks: ImageButton = findViewById(R.id.nav_tasks)
        val navTimer: ImageButton = findViewById(R.id.nav_timer)
        val navReminders: ImageButton = findViewById(R.id.nav_reminders)
        val navProfile: ImageButton = findViewById(R.id.nav_profile)

        navTasks.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        navTimer.setOnClickListener {
            startActivity(Intent(this, TimerActivity::class.java))
        }

        navReminders.setOnClickListener {
            // Already in ReminderActivity
        }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        setReminderButton.setOnClickListener {
            val reminderText = reminderEditText.text.toString()
            val hour = timePicker.hour
            val minute = timePicker.minute
            if (reminderText.isNotEmpty()) {
                setReminder(reminderText, hour, minute)
            } else {
                reminderEditText.error = "Reminder text cannot be empty"
            }
        }
    }

    private fun setReminder(reminderText: String, hour: Int, minute: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderBroadcastReceiver::class.java).apply {
            putExtra("reminderText", reminderText)
        }


        val uniqueId = (System.currentTimeMillis() / 1000).toInt()

        val pendingIntent = PendingIntent.getBroadcast(
            this, uniqueId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Setting the reminder
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        // If passed , setting it for tomorrow
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        saveReminder(reminderText)

        Toast.makeText(this, "Reminder set for $reminderText at $hour:$minute", Toast.LENGTH_SHORT).show()
    }

    private fun saveReminder(reminderText: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("lastReminder", reminderText)
        editor.apply()
    }

    fun getSavedReminder(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("lastReminder", null)
    }
}
