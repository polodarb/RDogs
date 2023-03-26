package com.polodarb.rdogs.data.model

data class ListBreedsModel(
    val status: String,
    val message: Map<String, List<String>>
)
