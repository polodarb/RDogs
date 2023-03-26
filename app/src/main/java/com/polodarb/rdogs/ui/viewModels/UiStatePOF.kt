package com.polodarb.rdogs.ui.viewModels

import com.polodarb.rdogs.data.remote.result.NetworkResult

sealed class UiStatePOF {
    object Loading : UiStatePOF()

    data class Success(val data: List<String>) : UiStatePOF()

    data class Error(val throwable: String) : UiStatePOF()
}