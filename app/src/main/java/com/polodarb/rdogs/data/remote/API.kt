package com.polodarb.rdogs.data.remote

import com.polodarb.rdogs.data.model.ListOfBreedsModel
import com.polodarb.rdogs.data.model.ParentModel
import com.polodarb.rdogs.data.model.PhotosOfBreedModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://dog.ceo/api/"

interface API {

    @GET("breeds/list/all")
    suspend fun getListAllBreeds(): Response<ListOfBreedsModel>

    @GET("breed/{breed}/images/random/12") // hound/afghan - breed/sub-breed
    suspend fun getPhotosByBreed(@Path("breed") breed: String): Response<PhotosOfBreedModel>

}