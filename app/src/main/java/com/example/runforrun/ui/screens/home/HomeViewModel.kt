package com.example.runforrun.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.runforrun.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

}