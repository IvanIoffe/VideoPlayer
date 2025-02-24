package com.ioffeivan.videoplayer.video.data.source.remote

import com.ioffeivan.videoplayer.core.data.Result
import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoListDto
import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoResponseDto
import com.ioffeivan.videoplayer.video_player.domain.model.VideoRequest
import kotlinx.coroutines.flow.Flow

interface VideoRemoteDataSource {
    fun getVideoList(): Flow<Result<VideoListDto>>
    fun getVideoById(videoRequest: VideoRequest): Flow<Result<VideoResponseDto>>
}