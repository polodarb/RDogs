package com.polodarb.rdogs.data.repository

import com.polodarb.rdogs.data.remote.API
import com.polodarb.rdogs.utils.Utils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val remote: API) {

    suspend fun getBreedsListRemote() = flow<List<String>> {
        val result = remote.getListAllBreeds().body()?.message!!
        emit(Utils.listConverter(result))// Convert Map<*, List<*>> to List<*>
    }

    suspend fun getPhotosByBreedRemote() = flow<List<String>> {
        emit(remote.getPhotosByBreed().body()?.message!!)
    }

    // getBreedsListLocal

    // getPhotosByBreedLocal
}