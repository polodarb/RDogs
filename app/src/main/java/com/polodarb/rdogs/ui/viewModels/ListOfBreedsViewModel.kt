package com.polodarb.rdogs.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polodarb.rdogs.data.remote.util.onResult
import com.polodarb.rdogs.data.repository.ListBreedsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListOfBreedsViewModel @Inject constructor(
    private val listBreedsRepository: ListBreedsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiStateLOF>(UiStateLOF.Loading)
    val state: StateFlow<UiStateLOF> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _state.value = listBreedsRepository.getListBreeds().first()
                listBreedsRepository.updateListBreeds().onResult(
                    onSuccess = {
                        listBreedsRepository.getListBreeds().collect { uiStates ->
                            _state.value = uiStates
                        }
                    },
                    onFailure = { // is it normal?
                        listBreedsRepository.getListBreeds().collect { uiStates ->
                            _state.value = uiStates
                        }
                    }
                )
            }
        }
    }
}