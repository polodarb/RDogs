package com.polodarb.rdogs.ui.viewModels

import com.polodarb.rdogs.data.local.Breed

sealed class UiStateLOF {
    object Loading : UiStateLOF()

    data class Success(val data: List<Breed>) : UiStateLOF()

    data class Error(val throwable: String) : UiStateLOF()
}