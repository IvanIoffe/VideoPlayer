package com.ioffeivan.videoplayer.video_player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class VideoPlayerImpl : VideoPlayer {

    private var exoPlayer: ExoPlayer? = null

    override fun initialize(context: Context) {
        exoPlayer = ExoPlayer.Builder(context).build()
    }

    override fun attach(playerView: PlayerView) {
        playerView.player = exoPlayer
    }

    override fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer?.setMediaItem(mediaItem)
    }

    override fun prepare() {
        exoPlayer?.prepare()
    }

    override fun play() {
        exoPlayer?.play()
    }

    override fun pause() {
        exoPlayer?.pause()
    }

    override fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
    }

    override fun getCurrentPosition(): Long {
        return exoPlayer?.currentPosition ?: 0
    }

    override fun isPlaying(): Boolean {
        return exoPlayer?.isPlaying ?: false
    }

    override fun setPlayerListener(listener: Player.Listener) {
        exoPlayer?.addListener(listener)
    }
}