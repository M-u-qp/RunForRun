package com.example.runforrun.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.runforrun.data.db.RunDatabase
import com.example.runforrun.data.db.RunDatabase.Companion.RUN_DB_NAME
import com.example.runforrun.data.db.dao.RunDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val USER_PREFERENCES_FILE_NAME = "user_preferences"

    @Provides
    @Singleton
    fun providePreferenceDataStore(
        application: Application
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { application.preferencesDataStoreFile(USER_PREFERENCES_FILE_NAME)}
        )

    @Provides
    @Singleton
    fun provideRunDatabase(
        application: Application
    ): RunDatabase {
//        return Room.databaseBuilder(
        return Room.inMemoryDatabaseBuilder(
            context = application,
            klass = RunDatabase::class.java,
//            name = RUN_DB_NAME
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