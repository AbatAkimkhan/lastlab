package com.example.lastlab

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private val CHANNEL_ID = "music_channel"
    private val NOTIFICATION_ID = 1

    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "onCreate called")

        mediaPlayer = MediaPlayer.create(this, R.raw.song)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand called")

        if (intent?.action == "STOP") {
            Log.d("MyService", "STOP action received")
            stopSelf()
            return START_NOT_STICKY
        }

        startForegroundServiceWithNotification()
        mediaPlayer.start()

        return START_STICKY
    }

    private fun startForegroundServiceWithNotification() {
        Log.d("MyService", "Creating notification and starting foreground")

        val stopIntent = Intent(this, MyService::class.java).apply {
            action = "STOP"
        }

        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Музыка играет 🎵")
            .setContentText("Нажми, чтобы остановить")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.ic_launcher_foreground, "Стоп", stopPendingIntent)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        Log.d("MyService", "onDestroy called")

        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
