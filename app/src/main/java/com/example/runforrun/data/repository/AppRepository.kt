package com.example.runforrun.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.runforrun.data.db.dao.RunDao
import com.example.runforrun.data.model.Run
import com.example.runforrun.data.utils.SortOrder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val runDao: RunDao
) {
    suspend fun insertRun(run: Run) = runDao.insertRun(run)
    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)
    fun getSortedAllRun(sortOrder: SortOrder) = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        when (sortOrder) {
            SortOrder.DATE -> runDao.getAllRunSortByDate()
            SortOrder.DISTANCE -> runDao.getAllRunSortByDistance()
            SortOrder.DURATION -> runDao.getAllRunSortByDuration()
            SortOrder.CALORIES_BURNED -> runDao.getAllRunSortByCaloriesBurned()
            SortOrder.AVG_SPEED -> runDao.getAllRunSortByAvgSpeed()
        }
    }
}