package com.example.playerone.fragments.audio

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.playerone.R
import com.example.playerone.activities.MainActivity
import java.io.FileDescriptor

class Notification {


//

    // First, create the notification channel (for Android 8.0 and higher)
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val channelId = "my_channel_id"
//        val channelName = "My Channel"
//        val importance = NotificationManager.IMPORTANCE_HIGH
//        val channel = NotificationChannel(channelId, channelName, importance)
//        val notificationManager =
//            requireContext().getSystemService(NotificationManager::class.java)
//        notificationManager.createNotificationChannel(channel)
//    }
//
//    val retriever = MediaMetadataRetriever()
//    retriever.setDataSource(arguments?.getString("audio_path"))
//    val bitmap = retriever.frameAtTime
//
//    val sArtworkUri = Uri.parse(arguments?.getString("audio_art"))
//    // Next, create the notification
//    val pfd = requireContext().contentResolver
//        .openFileDescriptor(sArtworkUri, "r")
//
//    val fd: FileDescriptor = pfd!!.fileDescriptor
//
//    val intent = Intent(requireContext(), MainActivity::class.java)
//    val pendingIntent =
//        PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_MUTABLE)
//
//    val builder = NotificationCompat.Builder(requireContext(), "my_channel_id")
//        .setSmallIcon(R.drawable.ic_baseline_play_circle_24)
//        .setContentTitle("Player One")
//        .setContentText(arguments?.getString("audio_title"))
//        .setLargeIcon(BitmapFactory.decodeFileDescriptor(fd))
//        .setSound(null)
//        .setStyle(
//            NotificationCompat.BigPictureStyle()
//                .bigPicture(bitmap)
//        )
//        .setPriority(NotificationCompat.PRIORITY_HIGH)
//        .setStyle(
//            androidx.media.app.NotificationCompat.MediaStyle()
//                .setMediaSession(mediaSession.sessionToken)
//        )
//        .setVisibility(androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC)
//        .addAction(R.drawable.ic_baseline_skip_previous_24, "Prev", pendingIntent)
//        .addAction(R.drawable.ic_baseline_play_circle_24, "Play", pendingIntent)
//        .addAction(R.drawable.ic_baseline_skip_next_24, "Next", pendingIntent)
//
//    // Finally, show the notification
//    val notificationManager =
//        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager.notify(1, builder.build())



}