package com.polodarb.rdogs.data.remote.api

import com.polodarb.rdogs.data.model.ListBreedsModel
import com.polodarb.rdogs.data.remote.result.NetworkResult
import retrofit2.http.GET

interface ListBreedsAPI {

    @GET("breeds/list/all")
    suspend fun getListAllBreeds(): NetworkResult<ListBreedsModel>

}