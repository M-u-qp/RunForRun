package com.example.runforrun.ui.screens.all_runs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.runforrun.data.common.SortOrder
import com.example.runforrun.data.model.Run
import com.example.runforrun.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRunsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _sortOrder = MutableStateFlow(SortOrder.DATE)

    @OptIn(ExperimentalCoroutinesApi::class)
    val runList = _sortOrder.flatMapLatest {
        repository.getSortedPagedAllRun(it)
            .flow
            .cachedIn(viewModelScope)
    }
    private val _dialog = MutableStateFlow<Run?>(null)
    val dialog = _dialog.asStateFlow()

    fun setSortOrder(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
    }

    fun setDialog(run: Run?) {
        _dialog.value = run
    }

    fun deleteRun() = dialog.value?.let {
        viewModelScope.launch {
            _dialog.value = null
            repository.deleteRun(it)
        }
    }
}