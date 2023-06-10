package com.example.playerone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playerone.R
import android.os.Handler
import com.example.playerone.utils.Utils


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Utils().fullscreen(this)

        // Delay the transition to the main activity
        Handler().postDelayed({
            // Start the main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000) // Delay fo
    }
}