package com.polodarb.rdogs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Breed::class], version = 1) // exportSchema = false?
abstract class AppDatabase : RoomDatabase() {

    abstract fun breedsDao(): BreedsDao

}