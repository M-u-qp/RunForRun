package com.example.runforrun.ui.navigation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.runforrun.R

@Composable
fun RunBottomNavigation(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = containerColor
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { onItemClick(index) },
                icon = {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            bitmap = ImageBitmap.imageResource(id = item.icon),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = item.text, style = MaterialTheme.typography.labelSmall)

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(id = R.color.black),
                    unselectedTextColor = colorResource(id = R.color.black)
                )
            )
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)