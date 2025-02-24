package com.ioffeivan.videoplayer.video_player.domain.usecase

import com.ioffeivan.videoplayer.video.domain.repository.VideoRepository
import com.ioffeivan.videoplayer.video_player.domain.model.VideoRequest
import javax.inject.Inject

class GetVideoByIdUseCase @Inject constructor(
    private val videoRepository: VideoRepository,
) {
    operator fun invoke(videoRequest: VideoRequest) = videoRepository.getVideoById(videoRequest)
}