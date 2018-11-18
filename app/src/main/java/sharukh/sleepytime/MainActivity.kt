package sharukh.sleepytime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.content.IntentFilter
import android.content.Context.ACTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.ActivityManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.opengl.Visibility
import android.view.View
import java.util.*
import sharukh.sleepytime.R.mipmap.ic_launcher
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.os.Build
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myService = Intent(this, MyService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(myService)
        } else {
            startService(myService)
        }

        var service_tracker = findViewById<TextView>(R.id.service_tracker)
        if (isMyServiceRunning(MyService::class.java)) {
            service_tracker.visibility = View.VISIBLE
        } else {
            service_tracker.visibility = View.GONE
        }

        refresh.setOnClickListener {
            var sleepText: TextView = findViewById(R.id.activity_main_text_sleep)
            var wakeupText: TextView = findViewById(R.id.activity_main_text_wake_up)
            sleepText.text = Data.sdf.format(Date(Data.getSleepTime(this)))
            wakeupText.text = Data.sdf.format(Date(Data.getWakeUpTime(this)))
        }
    }

    override fun onResume() {
        super.onResume()

        var sleepText: TextView = findViewById(R.id.activity_main_text_sleep)
        var wakeupText: TextView = findViewById(R.id.activity_main_text_wake_up)
        sleepText.text = Data.sdf.format(Date(Data.getSleepTime(this)))
        wakeupText.text = Data.sdf.format(Date(Data.getWakeUpTime(this)))

    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}
