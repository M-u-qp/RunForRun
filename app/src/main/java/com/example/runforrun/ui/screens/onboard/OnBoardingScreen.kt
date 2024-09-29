package com.example.runforrun.ui.screens.onboard

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.ui.screens.onboard.components.OnBoardingBottomItem
import com.example.runforrun.ui.screens.onboard.components.OnBoardingFirstPage
import com.example.runforrun.ui.screens.onboard.components.OnBoardingHeader
import com.example.runforrun.ui.screens.onboard.components.OnBoardingSecondPage
import kotlinx.coroutines.launch

private const val TOTAL_PAGE = 2

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    onBoardingScreenEvent: OnBoardingScreenEvent,
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { TOTAL_PAGE })

    val user by viewModel.user.collectAsStateWithLifecycle()
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()

    if (doesUserExist == false) {
        Column(modifier = Modifier.fillMaxSize()) {
            OnBoardingHeader(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            )

            HorizontalPager(
                modifier = Modifier.weight(0.8f),
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> {
                        OnBoardingFirstPage(modifier = Modifier.padding(horizontal = 24.dp))
                    }

                    1 -> {
                        OnBoardingSecondPage(
                            name = user.name,
                            gender = user.gender,
                            weight = user.weight,
                            weeklyGoal = user.weeklyGoal,
                            nameChange = onBoardingScreenEvent::updateName,
                            genderChange = onBoardingScreenEvent::updateGender,
                            weightChange = onBoardingScreenEvent::updateWeight,
                            weeklyGoalChange = onBoardingScreenEvent::updateWeeklyGoal,
                        )
                    }
                }
            }
            OnBoardingBottomItem(
                onNextClicked = {
                    if (pagerState.currentPage < TOTAL_PAGE - 1) scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                    if (pagerState.currentPage == TOTAL_PAGE - 1) {
                        viewModel.saveUser {
                            navigateToHome()
                        }
                    }
                },
                onBackClicked = {
                    if (pagerState.currentPage > 0) scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                isLast = pagerState.currentPage == TOTAL_PAGE - 1
            )
        }
        LaunchedEffect(key1 = viewModel.error.value) {
            if (viewModel.error.value.isNotBlank()) {
                Toast.makeText(context, viewModel.error.value, Toast.LENGTH_SHORT).show()
                viewModel.resetError()
            }
        }
    }
}

