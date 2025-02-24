package com.ioffeivan.videoplayer.video.data.source.remote.di

import com.ioffeivan.videoplayer.video.data.source.remote.RetrofitVideoRemoteDataSource
import com.ioffeivan.videoplayer.video.data.source.remote.VideoRemoteDataSource
import com.ioffeivan.videoplayer.video.data.source.remote.service.VideoApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class VideoRemoteSourceModuleProvider {

    @Singleton
    @Provides
    fun provideVideoApiService(retrofitBuilder: Retrofit.Builder): VideoApiService {
        return retrofitBuilder
            .build()
            .create()
    }
}

@InstallIn(SingletonComponent::class)
@Module
interface VideoRemoteSourceModuleBinder {

    @Binds
    fun bindRetrofitVideoRemoteDataSource(
        retrofitVideoRemoteDataSource: RetrofitVideoRemoteDataSource
    ): VideoRemoteDataSource
}