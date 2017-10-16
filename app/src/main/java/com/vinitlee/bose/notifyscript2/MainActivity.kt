package com.vinitlee.bose.notifyscript2

import android.app.NotificationChannel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.R.string.no
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.content.Intent
import android.R.string.yes
import android.app.AlertDialog
import android.text.TextUtils
import android.content.ComponentName
import android.provider.Settings
import android.provider.Settings.Secure




class MainActivity : AppCompatActivity() {

    lateinit var nameText: TextView
    lateinit var titleText: TextView
    lateinit var bodyText: TextView
    lateinit var outputText: TextView

    private val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
    private val ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isNotificationServiceEnabled()){
            val enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog.show()
        }

        nameText = findViewById(R.id.nameText)
        titleText = findViewById(R.id.titleText)
        bodyText = findViewById(R.id.bodyText)

        outputText = findViewById(R.id.outputText)

        findViewById<Button>(R.id.button).setOnClickListener { sampleNotification() }
    }

    private fun sampleNotification() {
        val mBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("My notification")
                .setContentText("Hello World!")

        val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mNotificationId = 1
        mNotifyMgr.notify(mNotificationId, mBuilder.build())
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(contentResolver,
                ENABLED_NOTIFICATION_LISTENERS)
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Notification Permissions")
        alertDialogBuilder.setMessage("You need to let this app read notifications!")
        alertDialogBuilder.setPositiveButton("Okay",
                DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)) })
        alertDialogBuilder.setNegativeButton("Don't wanna!",
                DialogInterface.OnClickListener { dialog, id ->
                    // If you choose to not enable the notification listener
                    // the app. will not work as expected
                })
        return alertDialogBuilder.create()
    }
}
