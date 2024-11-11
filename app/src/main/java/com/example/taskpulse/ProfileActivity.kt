package com.example.taskpulse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val editProfileButton: Button = findViewById(R.id.editProfileButton)
        val changePasswordButton: Button = findViewById(R.id.changePasswordButton)
        val notificationSettingsButton: Button = findViewById(R.id.notificationSettingsButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)

        editProfileButton.setOnClickListener {
            Toast.makeText(this, "Edit Profile ", Toast.LENGTH_SHORT).show()
        }

        changePasswordButton.setOnClickListener {
            Toast.makeText(this, "Change Password ", Toast.LENGTH_SHORT).show()
        }

        notificationSettingsButton.setOnClickListener {
            Toast.makeText(this, "Notification Settings ", Toast.LENGTH_SHORT).show()
        }

        logoutButton.setOnClickListener {
            Toast.makeText(this, "Logout ", Toast.LENGTH_SHORT).show()
        }


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
            startActivity(Intent(this, ReminderActivity::class.java))
        }

        navProfile.setOnClickListener {
            // Already in ProfileActivity
        }
    }
}
