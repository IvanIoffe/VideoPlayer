package com.ioffeivan.videoplayer.video.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(

    @SerialName("video_id")
    val id: Int,

    @SerialName("video_name")
    val name: String,

    @SerialName("video_url")
    val url: String,

    @SerialName("video_thumb")
    val imageUrl: String,

    @SerialName("video_duration")
    val durationInSeconds: Int,
)
