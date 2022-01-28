package com.example.simpleblog.presentation.feature.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.simpleblog.presentation.feature.destinations.PostFormScreenDestination
import com.example.simpleblog.presentation.feature.home.components.PostList
import com.example.simpleblog.presentation.feature.postform.postFormResultKey
import com.example.simpleblog.presentation.ui.ResultState
import com.example.simpleblog.presentation.ui.UiEvent
import com.example.simpleblog.presentation.ui.components.ErrorView
import com.example.simpleblog.presentation.ui.components.LoadingDialog
import com.example.simpleblog.presentation.ui.components.MyTopAppBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterialApi::class)
@Destination(
    start = true,
)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val homeState = viewModel.homeState.collectAsState()

    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(postFormResultKey)
        ?.observe(lifecycleOwner, {
            if (it) {
                viewModel.onAction(HomeAction.FetchPosts)
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.remove<Boolean>(postFormResultKey)
            }
        })

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.Navigate -> {
                    navigator.navigate(it.direction)
                }
                else -> {}
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.onAction(HomeAction.NavigateTo(PostFormScreenDestination())) },
            backgroundColor = MaterialTheme.colors.primary,
            content = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }) {
        Column {
            MyTopAppBar(title = "Home")
            SwipeRefresh(
                modifier = Modifier.fillMaxSize(),
                onRefresh = {
                    viewModel.onAction(HomeAction.FetchPosts)
                },
                state = rememberSwipeRefreshState(homeState.value.fetchPostsResult is ResultState.Loading)
            ) {
                homeState.value.fetchPostsResult.let {
                    when (it) {
                        is ResultState.Success -> {
                            PostList(it.data, viewModel)
                        }
                        is ResultState.Failure -> {
                            ErrorView(
                                modifier = Modifier.fillMaxSize(),
                                message = it.myFailure.message,
                                onClickRetry = { viewModel.onAction(HomeAction.FetchPosts) }
                            )
                        }
                        else -> {
                        }
                    }
                }
            }
        }

        homeState.value.deletePostResult.let {
            when (it) {
                is ResultState.Loading -> {
                    LoadingDialog()
                }

                else -> {}
            }
        }
    }
}