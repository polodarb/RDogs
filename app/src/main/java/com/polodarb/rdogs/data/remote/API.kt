package com.polodarb.rdogs.data.remote

import com.polodarb.rdogs.data.model.ListOfBreedsModel
import com.polodarb.rdogs.data.model.PhotosByBreedModel
import dagger.Module
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

const val BASE_URL = "https://dog.ceo/api/"

interface API {

    @GET("breeds/list/all")
    suspend fun getListAllBreeds(): Response<ListOfBreedsModel>

    @GET("breed/{breed}/images/random/10") // hound/afghan - breed/sub-breed
    suspend fun getPhotosByBreed(): Response<PhotosByBreedModel>

}