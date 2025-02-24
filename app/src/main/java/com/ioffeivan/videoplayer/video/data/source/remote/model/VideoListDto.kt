package com.ioffeivan.videoplayer.video.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoListDto(

    @SerialName("items")
    val items: List<VideoDto>,
)
