package com.example.simpleblog.presentation.feature.home.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.example.simpleblog.domain.model.Post

@Composable
fun DeleteConfirmationDialog(
    openDialog: Pair<Boolean, Post?>,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    if (openDialog.first.not()) return
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text(text = "Batal")
            }
        },
        text = {
            Text(text = "Hapus post ${openDialog.second?.title}?")
        },
    )
}