package com.example.simpleblog.presentation.feature.postform

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.simpleblog.presentation.feature.postform.components.PostFormContainer
import com.example.simpleblog.presentation.ui.UiEvent
import com.example.simpleblog.presentation.ui.components.MyTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

const val postFormResultKey = "postFormResultKe"

@Destination(
    navArgsDelegate = PostFormScreenNavArgs::class
)
@Composable
fun PostFormScreen(
    navArgs: PostFormScreenNavArgs,
    navigator: DestinationsNavigator,
    navController: NavController,
    viewModel: PostFormViewModel = hiltViewModel()
) {
    viewModel.onAction(PostFormAction.OnInit(navArgs))
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.PopBackStack<*> -> {
                    if (it.result == true) {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(postFormResultKey, it.result)
                    }
                    navigator.popBackStack()
                }
                else -> {}
            }
        }
    }

    Scaffold {
        Column {
            MyTopAppBar(
                title = if (navArgs.post == null) "Add Post" else "Update Post",
                onPopBackStack = { navigator.popBackStack() })
            PostFormContainer(navArgs.post, viewModel)
        }
    }
}