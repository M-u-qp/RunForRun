package com.example.runforrun.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.runforrun.data.model.Run

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC")
    fun getAllRunSortByDate(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC")
    fun getAllRunSortByDistance(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC")
    fun getAllRunSortByDuration(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC")
    fun getAllRunSortByCaloriesBurned(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC")
    fun getAllRunSortByAvgSpeed(): PagingSource<Int, Run>
}