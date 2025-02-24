package com.ioffeivan.videoplayer.video.data.source.local

import com.ioffeivan.videoplayer.core.data.Result
import com.ioffeivan.videoplayer.video.data.source.local.model.VideoEntity
import kotlinx.coroutines.flow.Flow

interface VideoLocalDataSource {
    fun getVideoList(): Flow<Result<List<VideoEntity>>>
    suspend fun getCountVideo(): Int
    suspend fun addVideoList(videoList: List<VideoEntity>)
}