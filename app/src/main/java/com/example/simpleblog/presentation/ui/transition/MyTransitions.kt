package com.example.simpleblog.presentation.ui.transition

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

@ExperimentalAnimationApi
object MyTransitions {

    fun enterTransition(
        duration: Int = 500
    ): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { 1000 },
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(animationSpec = tween(durationMillis = duration))
    }

    fun exitTransition(
        duration: Int = 500
    ): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { -1000 },
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutSlowInEasing
            )
        ) + fadeOut(animationSpec = tween(durationMillis = duration))
    }

    fun popEnterTransition(
        duration: Int = 500
    ): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { -1000 },
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(animationSpec = tween(durationMillis = duration))
    }

    fun popExitTransition(
        duration: Int = 500
    ): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { 1000 },
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutSlowInEasing
            )
        ) + fadeOut(animationSpec = tween(durationMillis = duration))
    }
}
