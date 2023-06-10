package com.example.playerone.fragments.images.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playerone.fragments.images.models.ImageModel
import com.example.playerone.fragments.images.repository.ImagesRepository
import com.example.playerone.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ImagesViewModel(private val imagesRepository: ImagesRepository): ViewModel() {

    val imageResponseLiveData: LiveData<NetworkResult<ImageModel>>
        get() = imagesRepository.imageResponseLiveData

    fun getAllImages(){
        viewModelScope.launch(Dispatchers.IO) {
            imagesRepository.getAllImages()
        }
    }

}