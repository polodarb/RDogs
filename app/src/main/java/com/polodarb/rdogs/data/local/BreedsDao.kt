package com.polodarb.rdogs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BreedsDao {

    @Query("SELECT * FROM breed_table")
    fun getAllListOfBreeds(): List<Breed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfBreeds(breeds: List<Breed>)

    @Query("DELETE FROM breed_table")
    fun deleteAll()

}