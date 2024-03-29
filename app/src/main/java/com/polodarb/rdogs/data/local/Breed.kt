package com.polodarb.rdogs.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "breed_table", indices = [Index(value = ["breed"], unique = true)])
data class Breed (

    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "breed") val breed: String

)