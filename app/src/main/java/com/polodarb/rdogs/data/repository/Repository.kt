package com.polodarb.rdogs.data.repository

import android.content.Context
import android.util.Log
import com.polodarb.rdogs.data.local.Breeds
import com.polodarb.rdogs.data.local.BreedsDao
import com.polodarb.rdogs.data.remote.API
import com.polodarb.rdogs.ui.viewModels.UiState
import com.polodarb.rdogs.utils.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: API,
    private val local: BreedsDao,
    @ApplicationContext private val context: Context
) {

    fun getListBreeds() = flow {
        emit(UiState.Loading)

        emit(UiState.Success(local.getAllListOfBreeds().first()))

        val request = remote.getListAllBreeds()
        val result = request.body()

        if (request.isSuccessful && result != null) {
            val convertedList = Utils.listConverter(result.message)
            val newList = convertedList.map { breed ->
                Breeds(id = 0, breed = breed)
            }
            Log.wtf("TAG", "$newList")
            local.insertListOfBreeds(newList)
        } else {
            emit(UiState.Error("network_error"))
        }

        emitAll(local.getAllListOfBreeds().map {
            UiState.Success(it)
        })

    }.catch { e ->
        UiState.Error("error: ${e.stackTrace}")
    }

    private suspend fun getBreedsListRemote(): List<String> {
        val result = remote.getListAllBreeds().body()?.message!!
        return Utils.listConverter(result) // Convert Map<*, List<*>> to List<*>
    }

    private suspend fun getBreedsListLocal() = flow {
        emit(local.getAllListOfBreeds())
    }

    suspend fun getPhotosByBreedRemote() = flow<List<String>> {
        emit(remote.getPhotosByBreed().body()?.message!!)
    }

    // getPhotosByBreedLocal
}