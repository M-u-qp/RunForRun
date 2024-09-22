package com.example.runforrun.di

import android.app.Application
import androidx.room.Room
import com.example.runforrun.data.db.RunDatabase
import com.example.runforrun.data.db.dao.RunDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRunDatabase(
        application: Application
    ): RunDatabase {
        return Room.inMemoryDatabaseBuilder(
            context = application,
            klass = RunDatabase::class.java
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRunDao(
        runDatabase: RunDatabase
    ): RunDao = runDatabase.runDao
}