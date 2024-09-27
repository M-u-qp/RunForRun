package com.example.runforrun.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToOnBoarding: () -> Unit
) {
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()

    if (doesUserExist == true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "HOME")
        }
    }
    LaunchedEffect(key1 = doesUserExist) {
        if (doesUserExist == false) {
            navigateToOnBoarding()
        }
    }
}