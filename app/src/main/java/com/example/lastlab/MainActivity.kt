package com.example.lastlab

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lastlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        requestNotificationPermission()

        binding.btnStartService.setOnClickListener {
            Log.d("MainActivity", "Start button clicked")
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }

        binding.btnStopService.setOnClickListener {
            Log.d("MainActivity", "Stop button clicked")
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
        }

        binding.btnNextActivity.setOnClickListener {
            Log.d("MainActivity", "Next Activity button clicked")
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("MainActivity", "Creating notification channel")

            val channel = NotificationChannel(
                "music_channel", // тот же ID, что и в MyService
                "Музыкальный канал",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }
}
