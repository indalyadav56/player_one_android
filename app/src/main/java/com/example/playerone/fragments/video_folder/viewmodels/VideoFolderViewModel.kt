package com.example.playerone.fragments.video_folder.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playerone.fragments.video_folder.models.VideoFolderModel
import java.io.File

class VideoFolderViewModel(application: Application) : AndroidViewModel(application) {

    private val _folders = MutableLiveData<ArrayList<VideoFolderModel>>()
    val folders: LiveData<ArrayList<VideoFolderModel>>
        get() = _folders

    private val contentResolver = application.contentResolver

    @SuppressLint("Range")
    fun fetchFolders() {

        val folders = mutableSetOf<VideoFolderModel>()
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val selection = "${MediaStore.Video.Media.SIZE} > ?"
        val selectionArgs = arrayOf("0")
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                val file = File(data)
                val folder = file.parentFile.name
                val videoFolderModel = VideoFolderModel(folderName = folder)
                folders.add(videoFolderModel)
            } while (cursor.moveToNext())
            cursor.close()
        }

        _folders.value = ArrayList(folders)
    }

}