package com.example.runforrun.ui.screens.onboard

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.R
import com.example.runforrun.data.model.Gender
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
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()

    val user by viewModel.user.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = doesUserExist) {
        if (doesUserExist == true) {
            navigateToHome()
        }
    }

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

@Composable
fun OnBoardingHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        ) {
            Icon(
                modifier = Modifier.size(64.dp),
                bitmap = ImageBitmap.imageResource(R.drawable.main_app_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Composable
fun OnBoardingFirstPage(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = stringResource(id = R.string.run_together),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 30.sp
            )
        )
    }
}

@Composable
fun OnBoardingSecondPage(
    modifier: Modifier = Modifier,
    name: String,
    gender: Gender,
    weight: Float,
    weeklyGoal: Float,
    nameChange: (String) -> Unit,
    genderChange: (Gender) -> Unit,
    weightChange: (Float) -> Unit,
    weeklyGoalChange: (Float) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = nameChange,
            label = {
                Text(
                    text = stringResource(id = R.string.name),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OnBoardingGenderCard(
                modifier = Modifier,
                selectedGender = gender,
                cardGender = Gender.MALE,
                genderChange = genderChange
            )
            OnBoardingGenderCard(
                modifier = Modifier,
                selectedGender = gender,
                cardGender = Gender.FEMALE,
                genderChange = genderChange
            )
            OnBoardingGenderCard(
                modifier = Modifier,
                selectedGender = gender,
                cardGender = Gender.OTHER,
                genderChange = genderChange
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            value = if (weight > 0) weight.toString() else "",
            onValueChange = { if (it.isNotBlank()) weightChange(it.toFloat()) },
            label = {
                Text(
                    text = stringResource(id = R.string.weight),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            value = if (weeklyGoal > 0) weeklyGoal.toString() else "",
            onValueChange = { if (it.isNotBlank()) weeklyGoalChange(it.toFloat()) },
            label = {
                Text(
                    text = stringResource(id = R.string.weekly_goal),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
    }
}

@Composable
fun OnBoardingGenderCard(
    modifier: Modifier,
    selectedGender: Gender,
    cardGender: Gender,
    genderChange: (Gender) -> Unit
) {
    val isSelected = cardGender == selectedGender

    OutlinedCard(
        onClick = {
            genderChange(cardGender)
        },
        modifier = modifier,
        border = BorderStroke(
            1.dp, if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                bitmap = ImageBitmap.imageResource(
                    id = when (cardGender) {
                        Gender.MALE -> R.drawable.male
                        Gender.FEMALE -> R.drawable.female
                        Gender.OTHER -> R.drawable.no_male_no_female
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun OnBoardingBottomItem(
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit,
    isLast: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AnimatedVisibility(
            visible = isLast,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            IconButton(
                onClick = { onBackClicked() },
                modifier = Modifier
                    .border(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.primary,
                        width = 1.dp
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.run),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .graphicsLayer(scaleX = -1f)
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onNextClicked() },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(R.drawable.run),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
