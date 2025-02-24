package com.ioffeivan.videoplayer.video.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ioffeivan.videoplayer.video.data.source.local.model.VideoEntity

@Dao
interface VideoDao {

    @Query("SELECT * FROM video")
    suspend fun getVideoList(): List<VideoEntity>

    @Query("SELECT COUNT(*) FROM video")
    suspend fun getCountVideo(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVideoList(videoList: List<VideoEntity>)
}