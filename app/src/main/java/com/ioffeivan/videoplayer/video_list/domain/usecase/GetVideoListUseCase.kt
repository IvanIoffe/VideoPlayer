package com.ioffeivan.videoplayer.video_list.domain.usecase

import com.ioffeivan.videoplayer.video.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideoListUseCase @Inject constructor(
    private val videoRepository: VideoRepository,
) {
    suspend operator fun invoke() = videoRepository.getVideoList()
}