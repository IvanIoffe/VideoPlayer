package com.ioffeivan.videoplayer.video_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ioffeivan.videoplayer.core.data.Result
import com.ioffeivan.videoplayer.video.domain.model.Video
import com.ioffeivan.videoplayer.video_list.domain.usecase.GetVideoListUseCase
import com.ioffeivan.videoplayer.video_list.domain.usecase.RefreshVideoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val getVideoListUseCase: GetVideoListUseCase,
    private val refreshVideoListUseCase: RefreshVideoListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<VideoListState>(VideoListState.Initial)
    val state: StateFlow<VideoListState> = _state.asStateFlow()

    fun getVideoList() {
        viewModelScope.launch {
            getVideoListUseCase()
                .onEach {
                    _state.value = when (it) {
                        is Result.Success -> VideoListState.Content(videoList = it.data)
                        is Result.Error -> VideoListState.Error(message = it.message)
                        Result.Loading -> VideoListState.Loading
                    }
                }
                .collect()
        }
    }

    fun refreshVideoList(currentList: List<Video>?) {
        refreshVideoListUseCase()
            .onStart {
                _state.value = VideoListState.Refresh.Loading
            }
            .filterNot { it is Result.Loading }
            .onEach {
                when (it) {
                    is Result.Success -> {
                        _state.value = VideoListState.Refresh.Content(refreshVideoList = it.data)
                    }

                    is Result.Error -> {
                        _state.value =
                            VideoListState.Refresh.Error(oldVideoList = currentList ?: listOf())
                    }

                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }
}