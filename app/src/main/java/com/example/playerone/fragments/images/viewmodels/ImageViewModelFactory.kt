package com.example.playerone.fragments.images.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playerone.fragments.images.repository.ImagesRepository

class ImageViewModelFactory(private val repository: ImagesRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImagesViewModel(repository) as T
    }

}