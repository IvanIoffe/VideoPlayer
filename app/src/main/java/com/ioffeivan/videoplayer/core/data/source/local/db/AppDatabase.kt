package com.ioffeivan.videoplayer.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ioffeivan.videoplayer.video.data.source.local.dao.VideoDao
import com.ioffeivan.videoplayer.video.data.source.local.model.VideoEntity

@Database(entities = [VideoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}