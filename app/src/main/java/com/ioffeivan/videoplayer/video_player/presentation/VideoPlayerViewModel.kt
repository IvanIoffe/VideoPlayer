package com.ioffeivan.videoplayer.video_player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ioffeivan.videoplayer.core.data.Result
import com.ioffeivan.videoplayer.video.domain.model.Video
import com.ioffeivan.videoplayer.video_player.domain.model.VideoRequest
import com.ioffeivan.videoplayer.video_player.domain.usecase.GetVideoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getVideoByIdUseCase: GetVideoByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<VideoPlayerState>(VideoPlayerState.Initial)
    val state: StateFlow<VideoPlayerState> = _state.asStateFlow()

    fun getVideoById(videoRequest: VideoRequest) {
        getVideoByIdUseCase(videoRequest)
            .onEach {
                handleResult(it)
            }
            .launchIn(viewModelScope)
    }

    private fun handleResult(result: Result<Video>) {
        _state.value = when (result) {
            is Result.Success -> {
                VideoPlayerState.Success(result.data)
            }

            is Result.Error -> {
                VideoPlayerState.Error(result.message)
            }

            Result.Loading -> VideoPlayerState.Loading
        }
    }
}