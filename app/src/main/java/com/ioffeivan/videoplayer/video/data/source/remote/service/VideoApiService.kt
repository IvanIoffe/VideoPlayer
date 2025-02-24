package com.ioffeivan.videoplayer.video.data.source.remote.service

import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoListDto
import com.ioffeivan.videoplayer.video.data.source.remote.model.VideoResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VideoApiService {

    @GET("getvideolist")
    suspend fun getVideoList(): Response<VideoListDto>

    @GET("getvideobyid/{id}")
    suspend fun getVideoById(
        @Path("id") id: Int,
    ): Response<VideoResponseDto>
}