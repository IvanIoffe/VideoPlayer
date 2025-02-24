package com.ioffeivan.videoplayer.video.domain.repository

import com.ioffeivan.videoplayer.core.data.Result
import com.ioffeivan.videoplayer.video.domain.model.Video
import com.ioffeivan.videoplayer.video.domain.model.VideoList
import com.ioffeivan.videoplayer.video_player.domain.model.VideoRequest
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideoList(): Flow<Result<VideoList>>
    fun refreshVideoList(): Flow<Result<VideoList>>
    fun getVideoById(videoRequest: VideoRequest): Flow<Result<Video>>
}