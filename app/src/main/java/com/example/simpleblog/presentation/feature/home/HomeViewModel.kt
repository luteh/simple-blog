package com.example.simpleblog.presentation.feature.home

import androidx.lifecycle.viewModelScope
import com.example.simpleblog.common.BaseViewModel
import com.example.simpleblog.domain.usecase.DeletePostUseCase
import com.example.simpleblog.domain.usecase.DeletePostUseCaseParams
import com.example.simpleblog.domain.usecase.FetchPostsUseCase
import com.example.simpleblog.presentation.ui.ResultState
import com.example.simpleblog.presentation.ui.UiEvent
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase
) :
    BaseViewModel() {

    private val _homeState = MutableStateFlow(HomeState.initial())
    val homeState = _homeState.asStateFlow()

    init {
        onAction(HomeAction.FetchPosts)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.DeletePost -> deletePost(action.id)
            HomeAction.FetchPosts -> fetchPosts()
            is HomeAction.NavigateTo -> navigateTo(action.direction)
        }
    }

    private fun navigateTo(direction: Direction) {
        sendUiEvent(UiEvent.Navigate(direction))
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _homeState.update {
                it.copy(fetchPostsResult = ResultState.Loading())
            }
            val result = fetchPostsUseCase()
            result.fold(
                { failure ->
                    _homeState.update {
                        it.copy(fetchPostsResult = ResultState.Failure(failure))
                    }
                },
                { data ->
                    _homeState.update {
                        it.copy(fetchPostsResult = ResultState.Success(data))
                    }
                },
            )
        }
    }

    private fun deletePost(id: Int) {
        viewModelScope.launch {
            _homeState.update {
                it.copy(deletePostResult = ResultState.Loading())
            }
            val result = deletePostUseCase(DeletePostUseCaseParams(id))
            result.fold(
                { failure ->
                    _homeState.update {
                        it.copy(deletePostResult = ResultState.Failure(failure))
                    }
                    sendUiEvent(UiEvent.ShowToast(failure.message))
                },
                { data ->
                    _homeState.update {
                        it.copy(deletePostResult = ResultState.Success(data))
                    }
                    sendUiEvent(UiEvent.ShowToast("Hapus post ${data.title} berhasil!"))
                    fetchPosts()
                },
            )
        }
    }
}