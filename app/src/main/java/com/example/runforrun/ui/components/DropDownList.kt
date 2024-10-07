package com.example.runforrun.ui.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T : Any> DropDownList(
    modifier: Modifier = Modifier,
    stringTransform: (T) -> String = { it.toString() },
    itemSelected: (T) -> Unit,
    request: (Boolean) -> Unit,
    list: List<T>,
    opened: Boolean = false
) {
    DropdownMenu(
        modifier = modifier,
        expanded = opened,
        onDismissRequest = { request(false) }
    ) {
        list.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringTransform(it),
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.Start)
                    )
                },
                onClick = { itemSelected(it) }
            )
        }
    }
}