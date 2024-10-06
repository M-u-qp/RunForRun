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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.runforrun.R
import com.example.runforrun.data.model.Gender
import com.example.runforrun.data.model.User

@Composable
fun HomeTopBarHeader(
    modifier: Modifier = Modifier,
    user: User,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
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
            AsyncImage(
                model = user.img,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                fallback = painterResource(
                    id = when (user.gender) {
                        Gender.MALE -> {
                            R.drawable.male
                        }

                        Gender.FEMALE -> {
                            R.drawable.female
                        }

                        else -> {
                            R.drawable.no_male_no_female
                        }
                    }
                ),
                contentScale = ContentScale.Crop,
                colorFilter = if (user.img == null) {
                    ColorFilter.tint(color = tint)
                } else {
                    null
                }
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