package com.ioffeivan.videoplayer.video.domain.model

data class Video(
    val id: Int,
    val name: String,
    val url: String,
    val imageUrl: String,
    val formattedDuration: String,
)