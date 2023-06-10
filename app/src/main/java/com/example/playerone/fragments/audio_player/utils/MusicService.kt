package com.example.playerone.fragments.audio_player.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log


class MusicService : Service() {

    //    private val myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        // Return the communication channel to the service.
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Do work here, based on the contents of the intent
        val audioPath = intent.getStringExtra("audioPath")
        if (audioPath != null) {
            createMediaPlayer(audioPath)
        }
        return Service.START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
        super.onDestroy()
    }

    fun createMediaPlayer(audioPath: String) {
        try {
            if (mediaPlayer == null) mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(this, Uri.parse(audioPath))
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()

        } catch (e: Exception) {
            return
        }
    }


}

//class MusicService : Service(), AudioManager.OnAudioFocusChangeListener {
//
//    private val myBinder = MyBinder()
//    var mediaPlayer: MediaPlayer? = null
//
////      // Get a reference to the AudioManager
////        audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
////        mediaSession = MediaSessionCompat(requireContext(), "My Music")
//
//    override fun onCreate() {
//        super.onCreate()
//    }
//
//    override fun onBind(p0: Intent?): IBinder {
//        return myBinder
//    }
//
//    inner class MyBinder() : Binder() {
//
//        fun currentService(): MusicService {
//            return this@MusicService
//        }
//
//    }
//
//    fun createMediaPlayer(audioPath: String) {
//        try {
//
//            if(mediaPlayer == null) mediaPlayer = MediaPlayer()
//            mediaPlayer!!.reset()
//            mediaPlayer!!.setDataSource(this, Uri.parse(audioPath))
//            mediaPlayer!!.prepare()
//            mediaPlayer!!.start()
//
//        } catch (e: Exception) {
//            return
//        }
//    }
//
//    override fun onAudioFocusChange(focusChange: Int) {
//        if (focusChange <= 0) {
//            //pause music
//            mediaPlayer!!.pause()
//
//        }
//
//    }
//
//    //for making persistent
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        return START_STICKY
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mediaPlayer?.release()
//    }
//
//}