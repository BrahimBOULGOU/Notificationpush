package com.brash.notificationpush

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    val NOTIFICATION_ID = 0

    private lateinit var mNotifyManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotifChannel()

       notifyMe.setOnClickListener{
           val notifyBuilder = getNotificationBuilder()
           mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())
       }

        cancelMe.setOnClickListener{
            mNotifyManager.cancel(NOTIFICATION_ID)
        }

        updateMe.setOnClickListener{
            updateNotif()
        }
    }

    private fun updateNotif(){
       // val androidImage = BitmapFactory
             //   .decodeResource(resources, R.drawable.ic_stat)
        val notifyBuilder = getNotificationBuilder()
        notifyBuilder.setStyle(NotificationCompat.BigPictureStyle()
                //.bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!"))
       // val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION);
       // val updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT)
       // notifyBuilder.addAction(R.drawable.ic_stat, "Update Notification", updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())

    }
    private fun createNotifChannel(){
        mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(PRIMARY_CHANNEL_ID, "MyNotif", NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Notification from Mascot"
            }
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder{
        val notificationIntent = Intent(this, MainActivity::class.java)
        val  notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notifyBuilder = NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID).apply {
                 setContentTitle("You've been notified!")
                 setContentText("This is your notification text.")
                 setSmallIcon(R.drawable.ic_stat_name);
                 priority = NotificationCompat.PRIORITY_HIGH
                 setDefaults(NotificationCompat.DEFAULT_ALL)
                 setContentIntent(notificationPendingIntent)
                 setAutoCancel(true)
         }

         return  notifyBuilder
    }
}