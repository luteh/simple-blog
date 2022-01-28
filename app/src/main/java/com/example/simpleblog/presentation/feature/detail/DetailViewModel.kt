package com.example.simpleblog.presentation.feature.detail

import androidx.lifecycle.viewModelScope
import com.example.simpleblog.domain.usecase.FetchPostDetailUseCase
import com.example.simpleblog.domain.usecase.FetchPostDetailUseCaseParams
import com.example.simpleblog.presentation.feature.destinations.PostFormScreenDestination
import com.example.simpleblog.presentation.ui.ResultState
import com.example.simpleblog.presentation.ui.UiEvent
import com.example.simpleblog.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val fetchPostDetailUseCase: FetchPostDetailUseCase) :
    BaseViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    init {
        sendUiEvent(UiEvent.Init)
    }

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.OnClickEdit -> {
                _detailState.value.fetchPostDetailResult.let {
                    if (it is ResultState.Success) {
                        sendUiEvent(UiEvent.Navigate(PostFormScreenDestination(it.data)))
                    }
                }
            }
            DetailAction.OnRefresh -> {
                fetchPostDetail()
            }
            is DetailAction.OnInit -> {
                _detailState.update {
                    it.copy(id = action.id)
                }
                fetchPostDetail()
            }
        }
    }

    private fun fetchPostDetail() {
        viewModelScope.launch {
            _detailState.run {
                update { it.copy(fetchPostDetailResult = ResultState.Loading()) }

                val result = fetchPostDetailUseCase(FetchPostDetailUseCaseParams(id = value.id))

                result.fold(
                    { failure ->
                        update { it.copy(fetchPostDetailResult = ResultState.Failure(failure)) }
                    },
                    { post ->
                        update { it.copy(fetchPostDetailResult = ResultState.Success(post)) }
                    },
                )
            }
        }
    }
}