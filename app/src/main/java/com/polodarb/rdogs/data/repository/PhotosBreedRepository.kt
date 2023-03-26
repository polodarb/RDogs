package com.polodarb.rdogs.data.repository


import android.util.Log
import com.polodarb.rdogs.data.remote.api.PhotosBreedAPI
import com.polodarb.rdogs.data.remote.result.NetworkResult
import com.polodarb.rdogs.data.remote.util.onFailure
import javax.inject.Inject
import com.polodarb.rdogs.data.remote.util.Result
import javax.inject.Named

class PhotosBreedRepository @Inject constructor(
    @Named("photosBreedRetrofit") val remote: PhotosBreedAPI,
) {
    suspend fun getPhotosByBreedRemote(breed: String): NetworkResult<List<String>> {
        val response = remote.getPhotosByBreed(breed).onFailure { return it }
        return Result.Success(response.message)
    }

    suspend fun getPhotosBySubBreedRemote(breed: String, sunBreed: String): NetworkResult<List<String>> {
        val response = remote.getPhotosBySubBreed(breed, sunBreed).onFailure { return it }
        return Result.Success(response.message)
    }

}