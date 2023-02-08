package com.polodarb.rdogs.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {

        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "breeds_database"
        ).build()

    }

    @Provides
    fun providesDao(appDatabase: AppDatabase): BreedsDao {
        return appDatabase.breedsDao()
    }
}
@Database(entities = [Breeds::class], version = 1) // exportSchema = false?
abstract class AppDatabase : RoomDatabase() {

    abstract fun breedsDao(): BreedsDao

}