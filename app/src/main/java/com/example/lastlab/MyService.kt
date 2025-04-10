package com.example.lastlab

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private val CHANNEL_ID = "music_channel"
    private val NOTIFICATION_ID = 1

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.song)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "STOP" -> {
                stopSelf()
                return START_NOT_STICKY
            }
            "NEXT" -> {
                // Пока просто игнорим, можно добавить переключение трека
            }
            else -> {
                startForegroundServiceWithNotification()
                mediaPlayer.start()
            }
        }
        return START_STICKY
    }

    private fun startForegroundServiceWithNotification() {
        createNotificationChannel()

        val stopIntent = Intent(this, MyService::class.java).apply {
            action = "STOP"
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val nextIntent = Intent(this, MyService::class.java).apply {
            action = "NEXT"
        }
        val nextPendingIntent = PendingIntent.getService(
            this, 1, nextIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Музыка играет 🎵")
            .setContentText("Управляй прямо отсюда")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(0, "Next", nextPendingIntent)
            .addAction(0, "Стоп", stopPendingIntent)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Музыкальный канал",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    override fun onDestroy() {
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        stopForeground(true) // Удаляет уведомление
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
