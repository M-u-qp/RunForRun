package com.example.runforrun.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.runforrun.data.db.dao.RunDao
import com.example.runforrun.data.model.Run

@Database(entities = [Run::class], version = 1)
abstract class RunDatabase : RoomDatabase() {
    abstract val runDao: RunDao
}