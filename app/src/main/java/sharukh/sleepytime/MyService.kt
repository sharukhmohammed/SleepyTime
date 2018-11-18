package sharukh.sleepytime

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.util.Log
import android.support.v4.app.NotificationCompat
import android.os.Build
import android.support.annotation.RequiresApi


class MyService : Service() {
    private var receiver = MyReceiver()

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val builder = Notification.Builder(this, createNotificationChannel("Sme","Service"))
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Running Sleepy Time")
                .setSubText("Tap to go to settings and hide the notification")
                .setAutoCancel(true)

            val notification = builder.build()
            startForeground(1, notification)

        } else {

            val builder = NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Running Sleepy Time")
                .setSubText("Tap to go to settings and hide the notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

            val notification = builder.build()

            startForeground(1, notification)
        }
        return Service.START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        chan.setShowBadge(false)
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onCreate() {
        super.onCreate()


        // Add network connectivity change action.

        val screenStateFilter = IntentFilter()
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF)
        screenStateFilter.addAction(Intent.ACTION_USER_PRESENT)

        // Set broadcast receiver priority.
        screenStateFilter.priority = 100

        // Create a network change broadcast receiver.
        receiver = MyReceiver()
        registerReceiver(receiver, screenStateFilter)

        Log.d("UMM", "Service onCreate: screenOnOffReceiver is registered.")
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister screenOnOffReceiver when destroy.
        unregisterReceiver(receiver)
        Log.d("UMM", "Service onDestroy: screenOnOffReceiver is unregistered.")

    }

}
