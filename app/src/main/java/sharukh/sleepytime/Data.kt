package sharukh.sleepytime

import android.content.Context
import android.preference.Preference
import android.preference.PreferenceManager
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object Data {

    var sdf: SimpleDateFormat = SimpleDateFormat("EEEE, h:mm aa", Locale.ENGLISH)

    fun saveRegisteredLockTime(context: Context, registeredLockTime: Long) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("LOCK_TIME", registeredLockTime).apply()
    }

    fun saveRegisteredUnLockTime(context: Context, registeredUnLockTime: Long) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("UNLOCK_TIME", registeredUnLockTime)
            .apply()
    }

    fun getRegisteredLockTime(context: Context): Long {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("LOCK_TIME", 0L)
    }

    fun getRegisteredUnLockTime(context: Context): Long {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("UNLOCK_TIME", 0L)
    }

    fun setSleepTime(context: Context, regSleepTime: Long) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("SLEEP_TIME", regSleepTime).apply()
    }

    fun getSleepTime(context: Context): Long {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("SLEEP_TIME", 0L)
    }

    fun setWakeUpTime(context: Context, wakeupTime: Long) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("WAKEUP_TIME", wakeupTime).apply()
    }

    fun getWakeUpTime(context: Context): Long {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("WAKEUP_TIME", 0L)
    }

    fun getLongestIde(context: Context): Long {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong("LONGEST_IDLE", 0L)
    }

    fun setLongestIde(context: Context, longestTime: Long) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("LONGEST_IDLE", longestTime).apply()
    }


}