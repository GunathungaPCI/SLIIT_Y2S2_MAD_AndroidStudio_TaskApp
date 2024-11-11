package com.example.taskpulse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val loginButton: Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            Log.d(TAG, "Login button clicked")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
