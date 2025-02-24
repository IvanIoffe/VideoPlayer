package com.ioffeivan.videoplayer.video.data.repository

import com.ioffeivan.videoplayer.core.data.Result
import com.ioffeivan.videoplayer.core.data.mapper.mapToDomainFlow
import com.ioffeivan.videoplayer.video.data.mapper.toListVideoEntity
import com.ioffeivan.videoplayer.video.data.mapper.toVideo
import com.ioffeivan.videoplayer.video.data.mapper.toVideoList
import com.ioffeivan.videoplayer.video.data.source.local.VideoLocalDataSource
import com.ioffeivan.videoplayer.video.data.source.remote.VideoRemoteDataSource
import com.ioffeivan.videoplayer.video.domain.model.Video
import com.ioffeivan.videoplayer.video.domain.model.VideoList
import com.ioffeivan.videoplayer.video.domain.repository.VideoRepository
import com.ioffeivan.videoplayer.video_player.domain.model.VideoRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoRemoteDataSource: VideoRemoteDataSource,
    private val videoLocalDataSource: VideoLocalDataSource,
) : VideoRepository {

    override suspend fun getVideoList(): Flow<Result<VideoList>> {
        val countVideo = videoLocalDataSource.getCountVideo()
        return if (countVideo > 0) {
            videoLocalDataSource.getVideoList()
                .mapToDomainFlow { listVideoEntity ->
                    VideoList(items = listVideoEntity.map { it.toVideo() })
                }
        } else {
            getAndCacheVideoList()
        }
    }

    override fun refreshVideoList(): Flow<Result<VideoList>> {
        return getAndCacheVideoList()
    }

    override fun getVideoById(videoRequest: VideoRequest): Flow<Result<Video>> {
        return videoRemoteDataSource.getVideoById(videoRequest)
            .mapToDomainFlow { videoResponseDto ->
                videoResponseDto.toVideo()
            }
    }

    private fun getAndCacheVideoList(): Flow<Result<VideoList>> {
        return videoRemoteDataSource.getVideoList()
            .mapToDomainFlow(
                mapper = { videoListDto ->
                    videoListDto.toVideoList()
                },
                action = {
                    videoLocalDataSource.addVideoList(it.toListVideoEntity())
                }
            )
    }
}