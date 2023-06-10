package com.example.playerone.fragments.audio_player.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.playerone.R
import com.example.playerone.activities.MainActivity
import com.example.playerone.fragments.audio_player.ApplicationClass
import java.io.FileDescriptor

class MusicNotification {
    private lateinit var mediaSession : MediaSessionCompat


    fun initMusicNotification(mContext: Context, audio_path:String, audio_title:String, audio_art:String,) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "my_channel_id"
            val channelName = "My Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager =
                mContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(audio_path)
        val bitmap = retriever.frameAtTime

        mediaSession = MediaSessionCompat(mContext, "My Music")

        val sArtworkUri = Uri.parse(audio_art)

        val pfd = mContext.contentResolver
            .openFileDescriptor(sArtworkUri, "r")
        val fd: FileDescriptor = pfd!!.fileDescriptor

        val intent = Intent(mContext, MainActivity::class.java).setAction("next")
//        val intent = Intent("com.example.playerone.fragments.audio_player.ui.AudioPlayerFragment").setAction("next")
        val pendingIntent =
            PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(mContext, "my_channel_id")
            .setSmallIcon(R.drawable.ic_baseline_play_circle_24)
            .setContentTitle(audio_title)
            .setContentText(audio_title)
            .setLargeIcon(BitmapFactory.decodeFileDescriptor(fd))
            .setSound(null)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setProgress(100, 10, false)
            .setOnlyAlertOnce(false)
            .setSound(null)
            .setVisibility(androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(R.drawable.ic_baseline_skip_previous_24, "Prev", pendingIntent)
            .addAction(R.drawable.play_icon_48, "Play", pendingIntent)
            .addAction(R.drawable.ic_baseline_skip_next_24, "Next", pendingIntent)

        //    // Finally, show the notification
    val notificationManager =
        mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(1, builder.build())
    }




}