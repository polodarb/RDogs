package com.polodarb.rdogs.data.repository

import android.content.Context
import com.polodarb.rdogs.data.local.BreedsDao
import com.polodarb.rdogs.data.remote.api.ListBreedsAPI
import com.polodarb.rdogs.data.remote.util.onFailure
import com.polodarb.rdogs.ui.viewModels.UiStateLOF
import com.polodarb.rdogs.utils.Utils
import com.polodarb.rdogs.data.remote.result.NetworkResult
import kotlinx.coroutines.flow.Flow
import com.polodarb.rdogs.data.remote.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class ListBreedsRepository @Inject constructor(
    @Named("listBreedsRetrofit") val remote: ListBreedsAPI,
    @ApplicationContext contextApp: Context,
    val local: BreedsDao
) {

    fun getListBreeds(): Flow<UiStateLOF> = flow {
//        emit(UiState.Loading)
        emit(UiStateLOF.Success(local.getAllListOfBreeds()))
    }

    suspend fun updateListBreeds(): NetworkResult<Unit> {

        val listBreeds = remote.getListAllBreeds().onFailure { return it }
        val convertList = Utils.listConverter(listBreeds.message)

        local.deleteAll()
        local.insertListOfBreeds(convertList)

        return Result.Success(Unit)
    }
}