package com.example.playerone.fragments.audio_player.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.os.Handler
import android.util.Log

class AudioPlayerViewModel(application: Application) :AndroidViewModel(application) {

    private val handler = Handler()
    
    private val _seekBarProgress = MutableLiveData<Int>(0)
    val seekBarProgress: LiveData<Int> = _seekBarProgress

    suspend fun updateSeekBarProgress(progress: Int) {
        _seekBarProgress.postValue(progress)
    }


    private val updateSeekBarTask = object : Runnable {
        override fun run() {
            _seekBarProgress.value = _seekBarProgress.value?.plus(1)
            handler.postDelayed(this, 1000)
        }
    }

    fun startUpdatingSeekBar() {
        handler.post(updateSeekBarTask)
    }

    fun stopUpdatingSeekBar() {
        handler.removeCallbacks(updateSeekBarTask)
    }
}