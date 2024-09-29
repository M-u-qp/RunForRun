package com.example.runforrun.ui.screens.onboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.data.model.Gender

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