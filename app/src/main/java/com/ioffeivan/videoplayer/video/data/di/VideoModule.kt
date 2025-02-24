package com.ioffeivan.videoplayer.video.data.di

import com.ioffeivan.videoplayer.video.data.repository.VideoRepositoryImpl
import com.ioffeivan.videoplayer.video.domain.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface VideoModuleBinder {

    @Binds
    fun bindVideoRepositoryImpl(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository
}