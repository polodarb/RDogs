package com.polodarb.rdogs.data.repository

import android.content.Context
import com.polodarb.rdogs.data.remote.API
import com.polodarb.rdogs.ui.viewModels.UiState
import com.polodarb.rdogs.utils.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: API,
    @ApplicationContext private val context: Context
) {

    fun getListBreeds() = flow<UiState> {
        emit(UiState.Loading)
        try {
            if (getBreedsListLocal().isEmpty()) {
                emit(UiState.Success(getBreedsListRemote()))
            } else {
                emit(UiState.Success(getBreedsListLocal()))
            }
        } catch (e: Exception) {
            emit(UiState.Error("error"))
        }
    }

    private suspend fun getBreedsListRemote(): List<String> {
        val result = remote.getListAllBreeds().body()?.message!!
        return Utils.listConverter(result) // Convert Map<*, List<*>> to List<*>
    }

    private suspend fun getBreedsListLocal(): List<String> {
        return emptyList()
    }

    suspend fun getPhotosByBreedRemote() = flow<List<String>> {
        emit(remote.getPhotosByBreed().body()?.message!!)
    }

    // getPhotosByBreedLocal
}