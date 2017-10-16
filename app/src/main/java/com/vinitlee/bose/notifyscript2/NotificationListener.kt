package com.vinitlee.bose.notifyscript2

import android.app.Notification
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager



/**
 * Created by vinitlee on 10/12/2017.
 */
class NotificationListener : NotificationListenerService() {

    private val TAG = "NLService"

    override fun onBind(intent: Intent?): IBinder {
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        Log.i(TAG,"-----------------------\n" + sbn.toString())
        Log.i(TAG,sbn?.packageName.toString())
        Log.i(TAG,sbn?.notification.toString())
        Log.i(TAG,sbn?.notification?.actions.toString())

        Log.i(TAG,sbn?.notification?.extras.toString())
        //Bundle[{
        // android.title=My notification,
        // android.subText=null,
        // android.showChronometer=false,
        // android.icon=2131099740,
        // android.text=Hello World!,
        // android.progress=0,
        // android.progressMax=0,
        // android.showWhen=true,
        // android.rebuild.applicationInfo=ApplicationInfo{bad7fab com.vinitlee.bose.notifyscript2},
        // android.infoText=null,
        // android.originatingUserId=0,
        // android.progressIndeterminate=false}]

        Log.i(TAG,sbn?.notification?.tickerText.toString())
        Log.i(TAG,sbn?.notification?.extras?.get(Notification.EXTRA_TITLE).toString())

        val pm = applicationContext.packageManager
        val ai: ApplicationInfo? = pm.getApplicationInfo(sbn?.packageName.toString(), 0)

        val applicationName = (pm?.getApplicationLabel(ai) ?: "(unknown)") as String

        Log.i(TAG,"I think the application is " + applicationName)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)

        Log.i(TAG, sbn.toString())
    }
}