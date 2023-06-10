package com.example.playerone.fragments.audio_player.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when(intent?.action) {
            "prev" -> Toast.makeText(context, "Action clicked", Toast.LENGTH_SHORT).show()
            "play" -> Toast.makeText(context, "play", Toast.LENGTH_SHORT).show()
            "next" -> Toast.makeText(context, "Action clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
