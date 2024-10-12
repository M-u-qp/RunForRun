package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.data.model.User
import com.example.runforrun.ui.components.UserProfileImage

@Composable
fun HomeTopBarHeader(
    modifier: Modifier = Modifier,
    user: User
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserProfileImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                imgUri = user.img,
                gender = user.gender
            )
            Spacer(modifier = Modifier.size(12.dp))
            val hi = stringResource(id = R.string.hi)
            Text(
                text = buildAnnotatedString {
                    append("$hi, ")
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append(user.name)
                    }
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(16.dp))
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.settings),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}