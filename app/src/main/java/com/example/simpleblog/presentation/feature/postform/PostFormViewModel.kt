package com.example.simpleblog.presentation.feature.postform

import androidx.lifecycle.viewModelScope
import com.example.simpleblog.common.BaseViewModel
import com.example.simpleblog.domain.usecase.CreatePostUseCase
import com.example.simpleblog.domain.usecase.CreatePostUseCaseParams
import com.example.simpleblog.domain.usecase.UpdatePostUseCase
import com.example.simpleblog.domain.usecase.UpdatePostUseCaseParams
import com.example.simpleblog.presentation.ui.ResultState
import com.example.simpleblog.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostFormViewModel @Inject constructor(
    private val createPostUseCase: CreatePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase
) : BaseViewModel() {

    private val _postFormState = MutableStateFlow(PostFormState())
    val postFormState = _postFormState.asStateFlow()

    fun onAction(action: PostFormAction) {
        when (action) {
            is PostFormAction.OnChangeContent -> changeContent(action.content)
            is PostFormAction.OnChangeTitle -> changeTitle(action.title)
            is PostFormAction.OnClickAddUpdate -> {
                addOrUpdatePost()
            }
            is PostFormAction.OnInit -> {
                action.navArgs.post?.let { post ->
                    _postFormState.update {
                        it.copy(
                            title = post.title,
                            content = post.content,
                            id = post.id
                        )
                    }
                }

            }
        }
    }

    private fun addOrUpdatePost() {
        viewModelScope.launch {
            _postFormState.run {
                if (value.title.isEmpty() and value.content.isEmpty()) {
                    sendUiEvent(UiEvent.ShowToast("Title dan Content tidak boleh kosong!"))
                    return@launch
                }

                update {
                    it.copy(addUpdatePostResult = ResultState.Loading())
                }

                val result = if (value.id == null) {
                    createPostUseCase(CreatePostUseCaseParams(value.title, value.content))
                } else {
                    updatePostUseCase(
                        UpdatePostUseCaseParams(
                            value.id!!,
                            value.title,
                            value.content
                        )
                    )
                }

                result.fold(
                    { failure ->
                        update { it.copy(addUpdatePostResult = ResultState.Failure(failure)) }
                        sendUiEvent(UiEvent.ShowToast(failure.message))
                    },
                    { data ->
                        update { it.copy(addUpdatePostResult = ResultState.Success(data)) }
                        sendUiEvent(UiEvent.ShowToast("Berhasil!"))
                        sendUiEvent(UiEvent.PopBackStack(true))
                    },
                )
            }

        }
    }

    private fun changeTitle(title: String) {
        _postFormState.update { it.copy(title = title) }
    }

    private fun changeContent(content: String) {
        _postFormState.update { it.copy(content = content) }
    }
}