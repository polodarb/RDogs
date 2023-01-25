package com.polodarb.rdogs.data.remote

import com.polodarb.rdogs.data.model.ListOfBreedsModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

const val BASE_URL = "https://dog.ceo/api/"

interface API {

    @GET("breeds/list/all")
    suspend fun getListAllBreeds() : Response<ListOfBreedsModel>

}

object RetrofitObject {
    fun getInstance() : API {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }
}