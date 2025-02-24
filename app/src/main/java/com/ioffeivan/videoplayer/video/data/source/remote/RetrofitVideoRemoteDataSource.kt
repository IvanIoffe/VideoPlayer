package com.ioffeivan.videoplayer.video.data.source.remote

import com.ioffeivan.videoplayer.core.data.Result
import com.ioffeivan.videoplayer.core.data.apiRemoteRequestFlow
import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoListDto
import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoResponseDto
import com.ioffeivan.videoplayer.video.data.source.remote.service.VideoApiService
import com.ioffeivan.videoplayer.video_player.domain.model.VideoRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrofitVideoRemoteDataSource @Inject constructor(
    private val videoApiService: VideoApiService,
) : VideoRemoteDataSource {

    override fun getVideoList(): Flow<Result<VideoListDto>> {
        return apiRemoteRequestFlow {
            videoApiService.getVideoList()
        }
    }

    override fun getVideoById(videoRequest: VideoRequest,): Flow<Result<VideoResponseDto>> {
        return apiRemoteRequestFlow {
            videoApiService.getVideoById(
                id = videoRequest.id
            )
        }
    }
}