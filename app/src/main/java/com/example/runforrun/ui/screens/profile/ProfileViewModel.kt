package com.example.runforrun.ui.screens.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.R
import com.example.runforrun.data.repository.Repository
import com.example.runforrun.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    repository: Repository
) : ViewModel(), ProfileEvent {
    private val _state = MutableStateFlow(ProfileScreenState())
    val state = combine(
        repository.getTotalDistance(),
        repository.getTotalDuration(),
        repository.getTotalCaloriesBurned(),
        _state
    ) { distance, duration, calories, status ->
        status.copy(
            totalDistance = distance / 1000f,
            totalDuration = duration.toBigDecimal()
                .divide((3_600_000).toBigDecimal(), 2, RoundingMode.HALF_UP)
                .toFloat(),
            totalCaloriesBurned = calories
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ProfileScreenState()
    )

    init {
        userRepository.user.onEach { user ->
            _state.update { it.copy(user = user) }
        }.launchIn(viewModelScope)
    }

    override fun beginEdit() {
        _state.update { it.copy(editMode = true) }
    }

    override fun saveProfile(context: Context) {
        if (_state.value.user.name.isBlank()) {
            _state.update { it.copy(error = context.getString(R.string.name_cannot_be_missing)) }
            return
        }
        viewModelScope.launch {
            userRepository.updateUser(state.value.user)
            _state.update { it.copy(editMode = false) }
        }
    }

    override fun changeUserName(newName: String) {
        _state.update { it.copy(user = it.user.copy(name = newName)) }
    }

    override fun changeUserImage(newUri: Uri?) {
        _state.update { it.copy(user = it.user.copy(img = newUri)) }
    }

    override fun discardChanges() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    user = userRepository.user.first(),
                    editMode = false
                )
            }
        }
    }
}