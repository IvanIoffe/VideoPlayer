package com.ioffeivan.videoplayer.video.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponseDto(

    @SerialName("response")
    val videoDto: VideoDto,
)
