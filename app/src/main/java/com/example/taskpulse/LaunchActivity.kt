package com.example.taskpulse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_logo)

        val launchButton: Button = findViewById(R.id.launchButton)
        launchButton.setOnClickListener {

            val intent = Intent(this, Onboard::class.java)
            startActivity(intent)
        }
    }
}
