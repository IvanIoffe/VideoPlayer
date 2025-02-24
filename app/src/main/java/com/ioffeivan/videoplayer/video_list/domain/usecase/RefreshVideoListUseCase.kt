package com.ioffeivan.videoplayer.video_list.domain.usecase

import com.ioffeivan.videoplayer.video.domain.repository.VideoRepository
import javax.inject.Inject

class RefreshVideoListUseCase @Inject constructor(
    private val videoRepository: VideoRepository,
) {
    operator fun invoke() = videoRepository.refreshVideoList()
}