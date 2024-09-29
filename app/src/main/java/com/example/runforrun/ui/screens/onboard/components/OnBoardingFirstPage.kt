package com.example.runforrun.ui.screens.onboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.runforrun.R

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