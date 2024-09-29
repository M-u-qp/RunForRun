package com.example.runforrun.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.data.repository.UserRepository
import com.example.runforrun.ui.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    var startDestination by mutableStateOf("")
        private set

    private val doesUserExist = userRepository.doesUserExist
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )

    init {
        doesUserExist.onEach { user ->
                startDestination =
                    if (user != null && user == true) {
                        Route.RunNavigation.route
                    } else {
                        Route.AppStartNavigation.route
                    }
        }.launchIn(viewModelScope)

    }
}