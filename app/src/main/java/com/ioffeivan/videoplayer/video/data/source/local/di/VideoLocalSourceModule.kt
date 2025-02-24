package com.ioffeivan.videoplayer.video.data.source.local.di

import com.ioffeivan.videoplayer.core.data.source.local.db.AppDatabase
import com.ioffeivan.videoplayer.video.data.source.local.RoomVideoLocalDataSource
import com.ioffeivan.videoplayer.video.data.source.local.VideoLocalDataSource
import com.ioffeivan.videoplayer.video.data.source.local.dao.VideoDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class VideoLocalSourceModuleProvider {

    @Singleton
    @Provides
    fun provideVideoDao(appDatabase: AppDatabase): VideoDao {
        return appDatabase.videoDao()
    }
}

@InstallIn(SingletonComponent::class)
@Module
interface VideoLocalSourceModuleBinder {

    @Binds
    fun bindRoomVideoLocalDataSource(
        roomVideoLocalDataSource: RoomVideoLocalDataSource
    ): VideoLocalDataSource
}