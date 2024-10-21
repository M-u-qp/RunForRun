package com.example.runforrun.ui.screens.onboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.data.model.Gender

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
            onValueChange = { input ->
                if (input.isNotBlank() && input.matches(Regex("^[0-9]*\\.?[0-9]*\$"))) {
                    weightChange(input.toFloat())
                }
            },
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
            onValueChange = { input ->
                if (input.isNotBlank() && input.matches(Regex("^[0-9]*\\.?[0-9]*\$"))) {
                    weeklyGoalChange(input.toFloat())
                }
            },
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