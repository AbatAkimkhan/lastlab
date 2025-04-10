package com.example.lastlab

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity2 : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val char = intent?.getCharExtra("randomCharacter", '?')
            textView.append(char.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        textView = findViewById(R.id.textView)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)

        // Регистрируем приёмник с флагом NOT_EXPORTED
        ContextCompat.registerReceiver(
            this,
            receiver,
            IntentFilter("my.custom.action.tag.lab6"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        startButton.setOnClickListener {
            val intent = Intent(this, RandomCharacterService::class.java)
            startService(intent)
        }

        stopButton.setOnClickListener {
            val intent = Intent(this, RandomCharacterService::class.java)
            stopService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}
