package com.example.playerone.fragments.audio.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.*
import com.example.playerone.fragments.audio.models.AudioModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class AudioViewModel(application: Application) : AndroidViewModel(application) {

    private val _audios = MutableLiveData<ArrayList<AudioModel>>()

    val audios: LiveData<ArrayList<AudioModel>>
        get() = _audios


    private val contentResolver = application.contentResolver


    fun fetchAudios() {
        viewModelScope.launch {
            getAllAudios()
        }
    }

    @SuppressLint("Range")
    suspend fun getAllAudios() {
        val tempList = ArrayList<AudioModel>()
        val selection = MediaStore.Audio.Media.IS_MUSIC +  " != 0"
        val projection = arrayOf(MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID)
        val cursor = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,selection,null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC", null)

        if (cursor != null && cursor.moveToNext()) {

            do {
                val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))?:"Unknown"
                val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))?:"Unknown"
                val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))?:"Unknown"
                val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))?:"Unknown"
                val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
//                val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                val albumIdC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                val uri = Uri.parse("content://media/external/audio/albumart")
                val artUriC = Uri.withAppendedPath(uri, albumIdC).toString()
                val music = AudioModel(id = idC, title = titleC, path = pathC, artUri = Uri.parse(artUriC))
                val file = File(music.path)
                if(file.exists())
                    tempList.add(music)
            }while (cursor.moveToNext())
            cursor.close()

//            do {
//                val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
//                val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
////              val folderC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.BUCKET_DISPLAY_NAME))
//                val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
//                val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
//
////              val durationC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)).toLong()
//
//                try {
//                    val file = File(pathC)
//
//                    if (file.exists()) {
//                        val artUric = Uri.fromFile(file)
//                        val audio = AudioModel(
//                            title = titleC,
//                            id = idC,
//                            duration = 1000,
//                            size = sizeC,
//                            path = pathC,
//                            artUri = artUric
//                        )
//                        tempList.add(audio)
//                    }
//
//                } catch (e: Exception) {
//                }
//
//
//            } while (cursor.moveToNext())
//
//            cursor.close()
        }

        _audios.postValue(tempList)

    }


}