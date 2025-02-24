package com.ioffeivan.videoplayer.video.data.mapper

import com.ioffeivan.videoplayer.video.data.source.local.model.VideoEntity
import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoListDto
import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoDto
import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoResponseDto
import com.ioffeivan.videoplayer.video.domain.model.Video
import com.ioffeivan.videoplayer.video.domain.model.VideoList
import com.ioffeivan.videoplayer.video.utils.formatDuration

fun VideoResponseDto.toVideo() = videoDto.toVideo()

fun VideoListDto.toVideoList() =
    VideoList(
        items = items.map { videoRemote -> videoRemote.toVideo() }
    )

fun VideoDto.toVideo() =
    Video(
        id = id,
        name = name,
        url = url,
        imageUrl = imageUrl,
        formattedDuration = formatDuration(durationInSeconds),
    )

fun VideoListDto.toListVideoEntity(): List<VideoEntity> =
    items.map { it.toVideoEntity() }

fun VideoDto.toVideoEntity() =
    VideoEntity(
        id = id,
        name = name,
        url = url,
        imageUrl = imageUrl,
        formattedDuration = formatDuration(durationInSeconds),
    )

fun VideoEntity.toVideo() =
    Video(
        id = id,
        name = name,
        url = url,
        imageUrl = imageUrl,
        formattedDuration = formattedDuration,
    )