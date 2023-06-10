package com.example.playerone.fragments.audio.models

import android.net.Uri

data class AudioModel(
    val id:String,
    val title:String,
    val duration:Long = 0,
    val path:String,
    val artUri: Uri
    )
