package com.ioffeivan.videoplayer.video_list.presentation

import com.ioffeivan.videoplayer.video.domain.model.Video
import com.ioffeivan.videoplayer.video.domain.model.VideoList

sealed class VideoListState {
    data class Content(val videoList: VideoList): VideoListState()
    data class Error(val message: String): VideoListState()
    sealed class Refresh: VideoListState() {
        data class Content(val refreshVideoList: VideoList): Refresh()
        data class Error(val oldVideoList: List<Video>): Refresh()
        data object Loading: Refresh()
    }
    data object Loading: VideoListState()
    data object Initial: VideoListState()
}