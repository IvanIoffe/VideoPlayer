package com.ioffeivan.videoplayer.video.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String,
    val imageUrl: String,
    val formattedDuration: String,
)