package com.example.playerone.fragments.video.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playerone.fragments.video.models.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val _videos = MutableLiveData<ArrayList<VideoModel>>()

    val videos: LiveData<ArrayList<VideoModel>>
        get() = _videos



    private val contentResolver = application.contentResolver

    suspend fun fetchVideos(folderName:String) {
        withContext(Dispatchers.IO) {
            getVideos(folderName)
        }
    }


    @SuppressLint("Range")
    suspend fun getVideos(folderName:String) {
        withContext(Dispatchers.IO) {

            val tempList = ArrayList<VideoModel>()
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DURATION,
            )
            val selection = "${MediaStore.Video.Media.DATA} like ?"
            val selectionArgs = arrayOf("%$folderName%")
            val cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, MediaStore.Video.Media.DATE_ADDED + " DESC"
            )
            if (cursor != null && cursor.moveToNext()) {
                do {

                    val titleC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val folderC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
//                    val durationC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)).toLong()

                    try {
                        val file = File(pathC)

                        if (file.exists()) {
                            val artUric = Uri.fromFile(file)
                            val video = VideoModel(
                                title = titleC,
                                id = idC,
                                folderName = folderC,
                                duration = 1000,
                                size = sizeC,
                                path = pathC,
                                artUri = artUric
                            )
                            tempList.add(video)
                        }

                    } catch (e: Exception) {
                    }


                } while (cursor.moveToNext())

                cursor.close()
            }

            _videos.postValue(tempList)

        }
    }




}