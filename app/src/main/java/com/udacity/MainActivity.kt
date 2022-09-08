package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private var URL: String? = null
    private var fileName: String? = null

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            if(URL != null) {
                custom_button.buttonState = ButtonState.Loading
                download()
            }else {
                    Toast.makeText(applicationContext, "please select file", Toast.LENGTH_LONG).show()
            }
        }





    }





    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton && view.isChecked) {

            when (view.getId()) {
                R.id.radioButton1 -> URL = URL1
                R.id.radioButton2 -> URL = URL2
                R.id.radioButton3 -> URL = URL3
            }
            fileName = view.text.toString()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }

    private fun download() {

        val request = DownloadManager.Request(Uri.parse(URL))
            .setTitle(getString(R.string.app_name))
            .setDescription(getString(R.string.app_description))
            .setRequiresCharging(false)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    companion object {
        private const val URL1 =
            "https://github.com/bumptech/glide/archive/refs/heads/master.zip"

        private const val URL2 =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/masterXXX.zip"

        private const val URL3 =
            "https://github.com/square/retrofit/archive/refs/heads/master.zip"

        private const val CHANNEL_ID = "channelId"
    }

}
