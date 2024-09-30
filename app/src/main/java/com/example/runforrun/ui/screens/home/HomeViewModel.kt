package com.example.runforrun.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.data.repository.Repository
import com.example.runforrun.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository,
    userRepository: UserRepository
) : ViewModel() {


    private val distanceCovered = repository.getTotalDistance(

    )

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = combine(
        repository.getRunByDescDate(3),
        userRepository.user,
        _homeScreenState
    ) { runList, user, state ->
        state.copy(
            runList = runList,
            user = user
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        HomeScreenState()
    )

    val doesUserExist = userRepository.doesUserExist
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )
}