package sharukh.sleepytime

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import java.util.*

class MyReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        val nowLong = Calendar.getInstance().timeInMillis

        when (intent.action) {

            Intent.ACTION_SCREEN_OFF -> {
                Data.saveRegisteredLockTime(context, nowLong)
                Log.i("UMM","Got screen lock event")
            }

            Intent.ACTION_USER_PRESENT -> {
                Log.i("UMM","Got screen unlock event")

                Data.saveRegisteredUnLockTime(context, nowLong)

                val idle = Data.getLongestIde(context)
                val unlockTime = Data.getRegisteredUnLockTime(context)
                val lockTime = Data.getRegisteredLockTime(context)
                if (unlockTime > lockTime) {
                    if ((unlockTime - lockTime) > idle)
                    {
                        Log.w("UMM","Saving new time")
                        Data.setLongestIde(context, unlockTime - lockTime)
                        Data.setSleepTime(context, lockTime)
                        Data.setWakeUpTime(context, unlockTime)
                    } else {
                        Log.w("UMM", "Not saved")
                    }
                } else {
                    Toast.makeText(context, "unlockTime > lockTime", Toast.LENGTH_LONG).show()
                    Log.w("UMM","Shouldn't be happening")
                    Data.saveRegisteredUnLockTime(context, nowLong)
                    Data.saveRegisteredLockTime(context, nowLong + 10000)
                }
            }

        }

    }
}
