package com.ioffeivan.videoplayer.video_player.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import com.ioffeivan.videoplayer.core.utils.Constants
import com.ioffeivan.videoplayer.core.utils.isLandscape
import com.ioffeivan.videoplayer.databinding.FragmentVideoPlayerBinding
import com.ioffeivan.videoplayer.video_player.VideoPlayer
import com.ioffeivan.videoplayer.video_player.VideoPlayerImpl
import com.ioffeivan.videoplayer.video_player.domain.model.VideoRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {
    private lateinit var binding: FragmentVideoPlayerBinding

    private val videoPlayerViewModel: VideoPlayerViewModel by viewModels()

    private var videoPlayer: VideoPlayer? = null

    private val videoId by lazy {
        arguments?.getInt(Constants.KEY_VIDEO_ID) ?: 0
    }

    private var orientation: Int? = null

    private var currentPosition: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong(Constants.KEY_CURRENT_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orientation = this.resources.configuration.orientation

        initializeVideoPlayer()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                videoPlayerViewModel.state.collect {
                    handleState(it)
                }
            }
        }

        binding.playerViewVideoPlayerFragment.setControllerVisibilityListener(
            PlayerView.ControllerVisibilityListener { visibility ->
                if (isLandscape(orientation)) {
                    binding.textViewVideoName.visibility = visibility
                }
            }
        )

        binding.videoPlayerFragmentError.buttonRepeatLoading.setOnClickListener {
            getVideoById()
        }
    }

    override fun onResume() {
        super.onResume()
        videoPlayer?.play()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoPlayer?.release()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(Constants.KEY_CURRENT_POSITION, videoPlayer?.getCurrentPosition() ?: 0)
        super.onSaveInstanceState(outState)
    }

    private fun initializeVideoPlayer() {
        videoPlayer = VideoPlayerImpl().apply {
            initialize(context = requireContext())
            attach(playerView = binding.playerViewVideoPlayerFragment)
        }
    }

    private fun handleState(state: VideoPlayerState) {
        when (state) {
            is VideoPlayerState.Success -> handleSuccessState(state)
            is VideoPlayerState.Error -> handleErrorState()
            VideoPlayerState.Loading -> handleLoadingState()
            VideoPlayerState.Initial -> handleInitialState()
        }
    }

    private fun handleSuccessState(state: VideoPlayerState.Success) {
        val video = state.video
        binding.textViewVideoName.visibility = View.VISIBLE
        binding.textViewVideoName.text = video.name
        videoPlayer?.apply {
            setMediaItem(MediaItem.fromUri(video.url))
            seekTo(currentPosition ?: 0)
            prepare()
            play()
        }

        binding.videoPlayerFragmentContent.visibility = View.VISIBLE
        binding.videoPlayerFragmentLoading?.visibility = View.INVISIBLE
        stopShimmer()
    }

    private fun handleErrorState() {
        binding.videoPlayerFragmentLoading?.visibility = View.INVISIBLE
        stopShimmer()
        binding.videoPlayerFragmentError.root.visibility = View.VISIBLE
    }

    private fun handleLoadingState() {
        binding.videoPlayerFragmentLoading?.visibility = View.VISIBLE
        startShimmer()
        binding.videoPlayerFragmentError.root.visibility = View.INVISIBLE
    }

    private fun handleInitialState() {
        getVideoById()
    }

    private fun getVideoById() {
        videoPlayerViewModel.getVideoById(
            VideoRequest(id = videoId)
        )
    }

    private fun startShimmer() {
        binding.shimmerFrameLayoutTextViewVideoName?.startShimmer()
    }

    private fun stopShimmer() {
        binding.shimmerFrameLayoutTextViewVideoName?.stopShimmer()
    }
}