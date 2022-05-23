package hseapp.app.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import hseapp.app.MainActivity
import splitties.systemservices.alarmManager
import kotlin.system.exitProcess

/**
 * штука для автоматического рестарта приложения при краше
 * (выключена, напиши - скажу где включить если надо)
 */
class AutoRestartCrashHandler(
    private val activity: MainActivity
) : Thread.UncaughtExceptionHandler {
    
    override fun uncaughtException(p0: Thread, p1: Throwable) {
        val intent = Intent(activity, MainActivity::class.java).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }
        val pendingIntent = PendingIntent.getActivity(
            activity, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent)
        
        activity.finish()
        exitProcess(2)
    }
}