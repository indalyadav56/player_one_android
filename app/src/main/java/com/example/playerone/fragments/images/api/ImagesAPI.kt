package com.example.playerone.fragments.images.api

import com.example.playerone.fragments.images.models.ImageModel
import retrofit2.Response
import retrofit2.http.GET


interface ImagesAPI {

    @GET("list?page=2&limit=100")
    suspend fun getAllImages() : Response<ImageModel>
}