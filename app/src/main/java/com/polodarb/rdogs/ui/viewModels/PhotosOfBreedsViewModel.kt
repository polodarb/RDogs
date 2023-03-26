package com.polodarb.rdogs.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polodarb.rdogs.data.remote.util.onResult
import com.polodarb.rdogs.data.repository.PhotosBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosOfBreedsViewModel @Inject constructor(
    private val photosBreedRepository: PhotosBreedRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiStatePOF>(UiStatePOF.Loading)
    val state: StateFlow<UiStatePOF> = _state.asStateFlow()

    fun getPhotosByBreed(breed: String) {
        viewModelScope.launch {
            photosBreedRepository.getPhotosByBreedRemote(breed).onResult(
                onSuccess = {
                    _state.value = UiStatePOF.Success(it.success)
                },
                onFailure = {
                    _state.value = UiStatePOF.Error("err")
                }
            )
        }
    }

    fun getPhotosBySubBreed(breed: String, subBreed: String) {
        viewModelScope.launch {
            photosBreedRepository.getPhotosBySubBreedRemote(breed, subBreed).onResult(
                onSuccess = {
                    _state.value = UiStatePOF.Success(it.success)
                },
                onFailure = {
                    _state.value = UiStatePOF.Error("err")
                }
            )
        }
    }

}