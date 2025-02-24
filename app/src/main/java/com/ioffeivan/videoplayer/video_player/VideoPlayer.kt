package com.ioffeivan.videoplayer.video_player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView

interface VideoPlayer {
    fun initialize(context: Context)
    fun attach(playerView: PlayerView)
    fun setMediaItem(mediaItem: MediaItem)
    fun prepare()
    fun play()
    fun pause()
    fun release()
    fun seekTo(position: Long)
    fun getCurrentPosition(): Long
    fun isPlaying(): Boolean
    fun setPlayerListener(listener: Player.Listener)
}