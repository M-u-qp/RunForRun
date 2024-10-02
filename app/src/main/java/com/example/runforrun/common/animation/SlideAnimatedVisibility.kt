package com.example.runforrun.common.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

object SlideAnimatedVisibility {
    private const val DOWN_IN_DURATION = 250
    private const val DOWN_OUT_DURATION = 250

    @Composable
    fun SlideUp(
        modifier: Modifier = Modifier,
        inDuration: Int = DOWN_IN_DURATION,
        outDuration: Int = DOWN_OUT_DURATION,
        visible: Boolean,
        content: @Composable AnimatedVisibilityScope.() -> Unit
    ) {
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = inDuration,
                    easing = LinearOutSlowInEasing
                )
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = outDuration,
                    easing = FastOutLinearInEasing
                )
            ),
            content = content
        )
    }
}