package com.example.simpleblog.presentation.feature.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpleblog.common.orZero
import com.example.simpleblog.common.toFormattedDate
import com.example.simpleblog.domain.model.Post
import com.example.simpleblog.presentation.feature.destinations.DetailScreenDestination
import com.example.simpleblog.presentation.feature.home.HomeAction
import com.example.simpleblog.presentation.feature.home.HomeViewModel
import com.example.simpleblog.presentation.ui.components.HtmlText

@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalMaterialApi
@Composable
fun PostList(data: List<Post>, viewModel: HomeViewModel) {
    var openDialog by rememberSaveable { mutableStateOf(Pair<Boolean, Post?>(false, null)) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(data) {
            Card(modifier = Modifier.fillMaxWidth(), onClick = {
                viewModel.onAction(HomeAction.NavigateTo(DetailScreenDestination(it.id)))
            }) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Published at: ${it.publishedAt.toFormattedDate()}",
                            fontSize = 10.sp
                        )
                        IconButton(onClick = {
                            openDialog = Pair(true, it)
                        }, modifier = Modifier.size(20.dp).padding(0.dp)) {
                            Icon(Icons.Outlined.Delete, contentDescription = "Delete")
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = it.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    HtmlText(it.content, maxLines = 3, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }

    DeleteConfirmationDialog(openDialog = openDialog, onConfirm = {
        viewModel.onAction(HomeAction.DeletePost(openDialog.second?.id.orZero()))
        openDialog = Pair(false, null)
    }, onDismissRequest = {
        openDialog = Pair(false, null)
    })
}