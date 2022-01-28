package com.example.simpleblog.presentation.feature.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.simpleblog.presentation.feature.detail.components.DetailContainer
import com.example.simpleblog.presentation.feature.postform.postFormResultKey
import com.example.simpleblog.presentation.ui.UiEvent
import com.example.simpleblog.presentation.ui.components.MyTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@Destination(navArgsDelegate = DetailScreenNavArgs::class)
@Composable
fun DetailScreen(
    navArgs: DetailScreenNavArgs,
    navigator: DestinationsNavigator,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(postFormResultKey)
        ?.observe(lifecycleOwner, {
            if (it) {
                viewModel.onAction(DetailAction.OnRefresh)
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
                is UiEvent.Init -> {
                    viewModel.onAction(DetailAction.OnInit(navArgs.id))
                }
                else -> {}
            }
        }
    }

    Scaffold {
        Column {
            MyTopAppBar(
                title = "Detail",
                onPopBackStack = {
                    navigator.popBackStack()
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.onAction(DetailAction.OnClickEdit)
                    }) {
                        Icon(Icons.Outlined.Edit, contentDescription = "Edit")
                    }
                }
            )
            DetailContainer(viewModel)
        }
    }
}