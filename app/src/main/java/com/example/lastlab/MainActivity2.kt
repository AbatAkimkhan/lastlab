package com.example.lastlab

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class MainActivity2 : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var receiver: BroadcastReceiver
    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        textView = findViewById(R.id.textView)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)

        // Приёмник для получения символа
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val char = intent?.getCharExtra("randomCharacter", '?')
                textView.text = char.toString()
            }
        }

        // Кнопка "Start"
        startButton.setOnClickListener {
            val intent = Intent(this, RandomCharacterService::class.java)
            startService(intent)
        }

        // Кнопка "Stop"
        stopButton.setOnClickListener {
            val intent = Intent(this, RandomCharacterService::class.java)
            stopService(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("my.custom.action.tag.lab6")
        registerReceiver(receiver, filter, RECEIVER_NOT_EXPORTED)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}
