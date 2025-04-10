package com.example.lastlab

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlin.concurrent.thread
import kotlin.random.Random

class RandomCharacterService : Service() {

    @Volatile
    private var isRunning = false

    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val TAG = "RandomCharacterService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext, "Random Service Started", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Service started...")

        if (isRunning) return START_STICKY
        isRunning = true

        thread(start = true) {
            while (isRunning) {
                try {
                    Thread.sleep(1000)
                    val randomChar = alphabet[Random.nextInt(alphabet.length)]
                    Log.i(TAG, "Generated char: $randomChar")

                    val broadcastIntent = Intent("my.custom.action.tag.lab6")
                    broadcastIntent.putExtra("randomCharacter", randomChar)
                    sendBroadcast(broadcastIntent)

                } catch (e: InterruptedException) {
                    Log.e(TAG, "Thread interrupted: ${e.message}")
                    break
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        isRunning = false
        Toast.makeText(applicationContext, "Random Service Stopped", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Service destroyed...")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
