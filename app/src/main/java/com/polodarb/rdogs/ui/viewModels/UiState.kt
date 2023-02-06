package com.polodarb.rdogs.ui.viewModels

sealed class UiState {
    object Loading : UiState()

    data class Success(val data: List<String>) : UiState()

    data class Error(val throwable: String) : UiState()
}