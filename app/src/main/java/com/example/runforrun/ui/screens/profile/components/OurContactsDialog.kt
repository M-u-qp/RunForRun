package com.example.runforrun.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.runforrun.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OurContactsDialog(
    writeToEmail: () -> Unit,
    onDismiss: () -> Unit,
    email: String
) {
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation("email", email)
        withStyle(
            SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.W400
            )
        ) {
            append(email)
        }
        pop()
    }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Spacer(modifier = Modifier.size(32.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.contact_email) + ":",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                modifier = Modifier.clickable { writeToEmail() },
                text = "    $annotatedString",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue)
            )
        }
    }
}