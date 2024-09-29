package com.example.runforrun.ui.screens.onboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.data.model.Gender
import com.example.runforrun.data.model.User
import com.example.runforrun.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), OnBoardingScreenEvent {

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    init {
        userRepository.user
            .onEach { dbUser -> _user.update { dbUser } }
            .launchIn(viewModelScope)
    }

     val doesUserExist = userRepository.doesUserExist
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )

    private fun User.isUserValid(): Boolean {
        return name.isNotBlank() && weight > 0
    }

    override fun updateName(name: String) = _user.update { it.copy(name = name) }
    override fun updateGender(gender: Gender) = _user.update { it.copy(gender = gender) }
    override fun updateWeight(weight: Float) = _user.update { it.copy(weight = weight) }
    override fun updateWeeklyGoal(weeklyGoal: Float) =
        _user.update { it.copy(weeklyGoal = weeklyGoal) }

    fun saveUser(navigate: () -> Unit) {
        if (!user.value.isUserValid()) {
            _error.value = "Введите правильные данные"
            return
        }
        viewModelScope.launch {
            userRepository.updateUser(user.value)
            navigate()
        }
    }

    fun resetError() {
        _error.value = ""
    }
}