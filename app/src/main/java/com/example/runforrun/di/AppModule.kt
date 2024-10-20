package com.example.runforrun.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.runforrun.background.BackgroundTrackingImpl
import com.example.runforrun.common.utils.LocationUts
import com.example.runforrun.data.db.RunDatabase
import com.example.runforrun.data.db.RunDatabase.Companion.RUN_DB_NAME
import com.example.runforrun.data.db.dao.RunDao
import com.example.runforrun.data.tracking.location.LocationMonitoringImpl
import com.example.runforrun.data.tracking.timer.TimerImpl
import com.example.runforrun.domain.tracking.background.BackgroundTracking
import com.example.runforrun.domain.tracking.location.LocationMonitoring
import com.example.runforrun.domain.tracking.timer.Timer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        private const val USER_PREFERENCES_FILE_NAME = "user_preferences"
        private const val SHARED_PREFERENCES = "shared_preferences"


        @Provides
        @Singleton
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        }


        @Provides
        @Singleton
        fun providePreferenceDataStore(
            application: Application,
            scope: CoroutineScope
        ): DataStore<Preferences> =
            PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                    produceNewData = { emptyPreferences() }
                ),
                produceFile = { application.preferencesDataStoreFile(USER_PREFERENCES_FILE_NAME) },
                scope = scope.plus(Dispatchers.IO + SupervisorJob())
            )

        @Singleton
        @Provides
        fun provideFusedLocationProviderClient(
            application: Application
        ) = LocationServices.getFusedLocationProviderClient(application)

        @Singleton
        @Provides
        fun provideLocationMonitoring(
            application: Application,
            fusedLocationProviderClient: FusedLocationProviderClient
        ): LocationMonitoring {
            return LocationMonitoringImpl(
                fusedLocationProviderClient = fusedLocationProviderClient,
                context = application,
                locationRequest = LocationUts.locationRequestBuilder.build()
            )
        }

        @Provides
        @Singleton
        fun provideRunDatabase(
            application: Application
        ): RunDatabase {
            return Room.databaseBuilder(
//            return Room.inMemoryDatabaseBuilder(
                context = application,
                klass = RunDatabase::class.java,
                name = RUN_DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        @Singleton
        fun provideRunDao(
            runDatabase: RunDatabase
        ): RunDao = runDatabase.runDao

        @Provides
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Singleton
        @Provides
        fun provideCoroutineScope(
            defaultDispatcher: CoroutineDispatcher
        ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
    }

    @Binds
    @Singleton
    abstract fun provideBackgroundTracking(
        backgroundTracking: BackgroundTrackingImpl
    ): BackgroundTracking

    @Binds
    @Singleton
    abstract fun provideTimer(
        timer: TimerImpl
    ): Timer
}