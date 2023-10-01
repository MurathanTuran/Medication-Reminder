package com.turanapps.medicationreminder.Util

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.turanapps.medicationreminder.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AlarmReceiver: BroadcastReceiver() {

    @SuppressLint("ScheduleExactAlarm")
    override fun onReceive(context: Context?, intent: Intent?) {

        val vibrator = context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(10000)

        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        if(alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone.play()

        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(intent!!.getStringExtra("comment"))

        val builder = NotificationCompat.Builder(context, "MEDICATION_REMINDER_CHANNEL")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(intent.getStringExtra("header"))
            .setContentText(intent.getStringExtra("comment"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(bigTextStyle)

        val notificationManager = NotificationManagerCompat.from(context)

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManager.notify(intent.getIntExtra("id", 0), builder.build())
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(10000)
            ringtone.stop()
        }

    }

}