package com.example.runforrun.ui.screens.statistics

import androidx.lifecycle.ViewModel
import com.example.runforrun.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunningStatisticsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
  

}