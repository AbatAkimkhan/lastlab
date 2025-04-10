package com.example.lastlab

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.util.*
import kotlin.concurrent.thread

class RandomCharacterService : Service() {

    private var isRunning = false
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
    private val TAG = "RandomCharacterService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext, "Random Service Started", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Service started...")

        isRunning = true

        thread {
            while (isRunning) {
                try {
                    Thread.sleep(1000)
                    val randomChar = alphabet.random()
                    Log.i(TAG, "Generated char: $randomChar")

                    val broadcastIntent = Intent("my.custom.action.tag.lab6")
                    broadcastIntent.putExtra("randomCharacter", randomChar)
                    sendBroadcast(broadcastIntent)
                } catch (e: InterruptedException) {
                    Log.i(TAG, "Thread interrupted")
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        Toast.makeText(applicationContext, "Random Service Stopped", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Service destroyed...")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
