package com.example.lastlab

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lastlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceIntent = Intent(this, MyService::class.java)

        binding.btnStartService.setOnClickListener {
            ContextCompat.startForegroundService(this, serviceIntent)
        }

        binding.btnStopService.setOnClickListener {
            stopService(serviceIntent)
        }

        binding.btnNextActivity.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }
}
