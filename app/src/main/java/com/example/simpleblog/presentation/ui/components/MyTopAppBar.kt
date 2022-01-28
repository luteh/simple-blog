package com.example.simpleblog.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun MyTopAppBar(
    title: String,
    onPopBackStack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        elevation = 4.dp,
        title = {
            Text(title)
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = if (onPopBackStack == null) null else {
            {
                IconButton(onClick = onPopBackStack) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
            }
        },
        actions = actions,
    )
}