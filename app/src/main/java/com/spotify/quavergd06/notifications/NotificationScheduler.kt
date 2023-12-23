package com.spotify.quavergd06.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import java.util.Locale
import androidx.core.app.NotificationCompat
import com.spotify.quavergd06.R
import com.spotify.quavergd06.view.LoginActivity
import com.spotify.quavergd06.view.home.HomeActivity
import kotlin.random.Random
import kotlin.random.asJavaRandom

class NotificationScheduler : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationScheduler", "Notification received")

        showNotification(context)
        // Configurar la próxima notificación con una hora aleatoria diferente
        setReminder(context, NotificationScheduler::class.java)
    }

    companion object {
        var numNotificacionesProgramadas = 0

        fun setReminder( context: Context, cls: Class<*>) {

            if (numNotificacionesProgramadas == 0){
                Log.d("NotificationScheduler", "Setting reminder")
                numNotificacionesProgramadas += 1
                Log.d("NotificationScheduler", "Numero de notificaciones pendientes: $numNotificacionesProgramadas")

                val intent = Intent(context, cls)
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


                // Obtener una nueva hora aleatoria del día
                val hour = Random.nextInt(24)
                val minute = Random.nextInt(60)

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                //calendar[Calendar.HOUR_OF_DAY] = hour + 8
                //calendar[Calendar.MINUTE] = minute

                calendar.add(Calendar.MINUTE, 1)


                // Verificar si la hora ya ha pasado hoy
                if (calendar.timeInMillis < System.currentTimeMillis()) {
                    // Si ha pasado, agregar un día a la fecha
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                }

                // Mostrar un Toast con la hora de la próxima notificación
                val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
                Toast.makeText(context, "Próxima notificación a las $formattedTime", Toast.LENGTH_LONG).show()
                Log.d("NotificationScheduler", "Próxima notificación a las $formattedTime")



                // Configurar una alarma exacta para la próxima notificación
                try {
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )

                } catch (e: SecurityException) {
                    // Manejar la excepción de seguridad
                    // Puedes mostrar un mensaje al usuario o realizar otras acciones apropiadas
                    Log.d("NotificationScheduler", "Cagamosssss")
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showNotification(context: Context) {
        Log.d("NotificationScheduler", "Showing notification")
        numNotificacionesProgramadas -=1
        Log.d("NotificationScheduler", "Numero de notificaciones pendientes: $numNotificacionesProgramadas")

        val intent = Intent (context, LoginActivity::class.java ).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val pendingIntent:PendingIntent = PendingIntent.getActivity(context,0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, HomeActivity.MY_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Recordatorio diario")
            .setContentText("Es la hora perfecta para capturar un momento.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = builder.build()

        notificationManager.notify(1, notification)
    }
}