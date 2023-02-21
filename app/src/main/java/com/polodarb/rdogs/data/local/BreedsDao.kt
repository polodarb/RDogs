package com.polodarb.rdogs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedsDao {

    @Query("SELECT * FROM breeds_table")
    fun getAllListOfBreeds(): Flow<List<Breeds>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfBreeds(breeds: List<Breeds>)

    @Query("DELETE FROM breeds_table")
    fun deleteAll()

}