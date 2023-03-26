package com.polodarb.rdogs.di

import android.app.Application
import androidx.room.Room
import com.polodarb.rdogs.data.local.AppDatabase
import com.polodarb.rdogs.data.local.BreedsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "breeds_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(
        groupDatabase: AppDatabase
    ): BreedsDao {
        return groupDatabase.breedsDao()
    }

}