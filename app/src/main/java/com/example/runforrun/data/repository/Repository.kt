package com.example.runforrun.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.runforrun.data.common.SortOrder
import com.example.runforrun.data.db.dao.RunDao
import com.example.runforrun.data.model.Run
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val runDao: RunDao
) {
    suspend fun insertRun(run: Run) = runDao.insertRun(run)
    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)
    fun getSortedPagedAllRun(sortOrder: SortOrder) = Pager(config = PagingConfig(pageSize = 10)) {
        when (sortOrder) {
            SortOrder.DATE -> runDao.getAllRunSortByDate()
            SortOrder.DISTANCE -> runDao.getAllRunSortByDistance()
            SortOrder.DURATION -> runDao.getAllRunSortByDuration()
            SortOrder.CALORIES_BURNED -> runDao.getAllRunSortByCaloriesBurned()
            SortOrder.AVG_SPEED -> runDao.getAllRunSortByAvgSpeed()
        }
    }

    suspend fun getRunningStatistics(fromDate: Date?, toDate: Date?) = runDao.getRunStats(fromDate, toDate)
    fun getRunByDescDate(limit: Int) = runDao.getRunByDescDate(limit)

    fun getTotalDistance(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalDistance(fromDate, toDate)

    fun getTotalDuration(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalDuration(fromDate, toDate)

    fun getTotalCaloriesBurned(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalCaloriesBurned(fromDate, toDate)

    fun getTotalAvgSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Float> =
        runDao.getTotalAvgSpeed(fromDate, toDate)
}