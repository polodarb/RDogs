package com.polodarb.rdogs.data.remote.api

import com.polodarb.rdogs.data.model.PhotosOfBreedModel
import com.polodarb.rdogs.data.remote.result.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotosBreedAPI {

    @GET("breed/{breed}/images/random/6")
    suspend fun getPhotosByBreed(@Path("breed") breed: String): NetworkResult<PhotosOfBreedModel>

    @GET("breed/{breed}/{subBreed}/images/random/6")
    suspend fun getPhotosBySubBreed(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
        ): NetworkResult<PhotosOfBreedModel>
}