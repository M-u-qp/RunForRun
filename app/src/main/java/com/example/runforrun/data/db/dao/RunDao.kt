package com.example.runforrun.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.runforrun.data.model.Run
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC")
    fun getAllRunSortByDate(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY distance DESC")
    fun getAllRunSortByDistance(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY duration DESC")
    fun getAllRunSortByDuration(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY caloriesBurned DESC")
    fun getAllRunSortByCaloriesBurned(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY avgSpeed DESC")
    fun getAllRunSortByAvgSpeed(): PagingSource<Int, Run>

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC LIMIT :limit")
    fun getRunByDescDate(limit: Int): Flow<List<Run>>

    @Query(
        "SELECT * FROM run_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    suspend fun getRunStats(fromDate: Date?, toDate: Date?): List<Run>

    //TOTALITY
    @Query(
        "SELECT TOTAL(distance) FROM run_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalDistance(fromDate: Date?, toDate: Date?): Flow<Long>
    @Query(
        "SELECT TOTAL(duration) FROM run_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalDuration(fromDate: Date?, toDate: Date?): Flow<Long>
    @Query(
        "SELECT TOTAL(caloriesBurned) FROM run_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalCaloriesBurned(fromDate: Date?, toDate: Date?): Flow<Long>
    @Query(
        "SELECT AVG(avgSpeed) FROM run_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalAvgSpeed(fromDate: Date?, toDate: Date?): Flow<Float>
}