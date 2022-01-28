package com.example.simpleblog.presentation.feature.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpleblog.common.toFormattedDate
import com.example.simpleblog.presentation.feature.detail.DetailAction
import com.example.simpleblog.presentation.feature.detail.DetailViewModel
import com.example.simpleblog.presentation.ui.ResultState
import com.example.simpleblog.presentation.ui.components.ErrorView
import com.example.simpleblog.presentation.ui.components.HtmlText
import com.example.simpleblog.presentation.ui.components.LoadingView

@Composable
fun DetailContainer(viewModel: DetailViewModel) {
    val detailState = viewModel.detailState.collectAsState()

    detailState.value.fetchPostDetailResult.let {
        when (it) {
            is ResultState.Failure -> {
                ErrorView(
                    modifier = Modifier.fillMaxSize(),
                    message = it.myFailure.message,
                    onClickRetry = { viewModel.onAction(DetailAction.OnRefresh) },
                )
            }
            is ResultState.Initial -> {}
            is ResultState.Loading -> LoadingView(modifier = Modifier.fillMaxSize())
            is ResultState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(
                        rememberScrollState()
                    )
                ) {
                    Text(
                        "Published at: ${it.data.publishedAt.toFormattedDate()}",
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it.data.title,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HtmlText(it.data.content)
                }
            }
        }
    }


}