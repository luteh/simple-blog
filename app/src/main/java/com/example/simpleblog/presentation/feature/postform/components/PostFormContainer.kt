package com.example.simpleblog.presentation.feature.postform.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.presentation.feature.postform.PostFormAction
import com.example.simpleblog.presentation.feature.postform.PostFormViewModel
import com.example.simpleblog.presentation.ui.ResultState
import com.example.simpleblog.presentation.ui.components.LoadingView

@Composable
fun PostFormContainer(post: Post?, viewModel: PostFormViewModel) {
    val postFormState = viewModel.postFormState.collectAsState()
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = postFormState.value.title,
            onValueChange = { viewModel.onAction(PostFormAction.OnChangeTitle(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Title") },
            placeholder = { Text("Title") },
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = postFormState.value.content,
            onValueChange = { viewModel.onAction(PostFormAction.OnChangeContent(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Content") },
            placeholder = { Text("Content") },
            maxLines = 10,
            )
        Spacer(Modifier.height(32.dp))
        when (postFormState.value.addUpdatePostResult) {
            is ResultState.Loading -> {
                LoadingView()
            }
            else -> {
                Button(
                    onClick = { viewModel.onAction(PostFormAction.OnClickAddUpdate) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(if (post == null) "Add Post" else "Update Post")
                }
            }
        }

    }

}