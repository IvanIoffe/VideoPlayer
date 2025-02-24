package com.ioffeivan.videoplayer.video.data.source.local

import com.ioffeivan.videoplayer.core.data.Result
import com.ioffeivan.videoplayer.core.data.localRequestFlow
import com.ioffeivan.videoplayer.video.data.source.local.dao.VideoDao
import com.ioffeivan.videoplayer.video.data.source.local.model.VideoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomVideoLocalDataSource @Inject constructor(
    private val videoDao: VideoDao,
) : VideoLocalDataSource {

    override fun getVideoList(): Flow<Result<List<VideoEntity>>> {
        return localRequestFlow {
            videoDao.getVideoList()
        }
    }

    override suspend fun getCountVideo(): Int = videoDao.getCountVideo()

    override suspend fun addVideoList(videoList: List<VideoEntity>,) {
        videoDao.addVideoList(videoList)
    }
}