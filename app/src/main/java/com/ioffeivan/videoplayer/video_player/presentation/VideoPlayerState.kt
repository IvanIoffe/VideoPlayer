package com.ioffeivan.videoplayer.video_player.presentation

import com.ioffeivan.videoplayer.video.domain.model.Video

sealed class VideoPlayerState {
    data class Success(val video: Video) : VideoPlayerState()
    data class Error(val message: String) : VideoPlayerState()
    data object Loading : VideoPlayerState()
    data object Initial : VideoPlayerState()
}