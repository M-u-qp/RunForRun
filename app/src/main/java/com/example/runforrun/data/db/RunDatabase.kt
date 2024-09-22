package com.example.runforrun.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.runforrun.data.db.dao.RunDao
import com.example.runforrun.data.model.Run

@Database(entities = [Run::class], version = 1)
abstract class RunDatabase : RoomDatabase() {
    companion object {
        const val RUN_DB_NAME = "run_db_name"
    }

    abstract val runDao: RunDao
}