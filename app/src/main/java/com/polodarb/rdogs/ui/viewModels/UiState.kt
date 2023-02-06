package com.polodarb.rdogs.ui.viewModels

interface UiState {
    object Loading : UiState

    data class Success(val data: List<String>) : UiState

    data class Error(val throwable: Throwable? = null) : UiState
}