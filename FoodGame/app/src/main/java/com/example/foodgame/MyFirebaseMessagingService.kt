package com.example.foodgame

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "LMEG"

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        // Puedes enviar este token a tu servidor para guardarlo y usarlo para enviar notificaciones a este dispositivo.
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Comprobar si el mensaje contiene una carga útil de datos.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            // Aquí puedes procesar los datos del mensaje.
        }

        // Comprobar si el mensaje contiene una carga útil de notificación.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // Mostrar la notificación.
            sendNotification(it.title, it.body)
        }
    }

    private fun sendNotification(title: String?, messageBody: String?) {
        val intent = Intent(this, Home::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = "my_channel_id" // Identificador del canal de notificación.
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.perfil) // Icono de la notificación.
            .setContentTitle(title) // Título de la notificación.
            .setContentText(messageBody) // Cuerpo del mensaje.
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Prioridad de la notificación.
            .setContentIntent(pendingIntent) // Intent que se ejecutará al hacer clic en la notificación.
            .setAutoCancel(true) // Eliminar la notificación al hacer clic.

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear el canal de notificación (necesario para Android 8.0 y superior).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, builder.build()) // Mostrar la notificación.
    }
}