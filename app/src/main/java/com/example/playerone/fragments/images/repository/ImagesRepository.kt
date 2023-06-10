package com.example.playerone.fragments.images.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playerone.fragments.images.api.ImagesAPI
import com.example.playerone.fragments.images.models.ImageModel
import com.example.playerone.utils.NetworkResult
import retrofit2.Response

class ImagesRepository(private val imagesAPI: ImagesAPI) {

    private val _imageResponseLiveData = MutableLiveData<NetworkResult<ImageModel>>()


    val imageResponseLiveData: LiveData<NetworkResult<ImageModel>>
        get() = _imageResponseLiveData


    suspend fun getAllImages(){
        _imageResponseLiveData.postValue(NetworkResult.Loading())
        val response = imagesAPI.getAllImages()
        handleResponse(response)
    }

    private fun handleResponse(response: Response<ImageModel>) {

        if (response.isSuccessful && response.body() != null) {
            _imageResponseLiveData.postValue(NetworkResult.Success(response.body()!!))

        }
        else if(response.errorBody()!=null){
            // val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _imageResponseLiveData.postValue(NetworkResult.Error("Error"))
        }
        else{
            _imageResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }

    }
}