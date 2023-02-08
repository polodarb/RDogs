package com.polodarb.rdogs.ui.viewModels

import com.polodarb.rdogs.data.local.Breeds

sealed class UiState {
    object Loading : UiState()

    data class Success(val data: List<Breeds>) : UiState()

    data class Error(val throwable: String) : UiState()
}