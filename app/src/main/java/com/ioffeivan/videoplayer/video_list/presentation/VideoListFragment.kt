package com.ioffeivan.videoplayer.video_list.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ioffeivan.videoplayer.R
import com.ioffeivan.videoplayer.core.utils.Constants
import com.ioffeivan.videoplayer.databinding.FragmentVideoListBinding
import com.ioffeivan.videoplayer.video.domain.model.Video
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoListFragment : Fragment(), VideoAdapter.Listener {
    private lateinit var binding: FragmentVideoListBinding

    private val videoListViewModel: VideoListViewModel by viewModels()

    private var videoAdapter: VideoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewVideoListShimmer()
        setupRecyclerViewVideoList()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                videoListViewModel.state.collect {
                    Log.d("videoListViewModel.state", "$it")
                    handleState(it)
                }
            }
        }

        binding.swipeRefreshLayoutVideoList.setOnRefreshListener {
            refreshVideoList()
        }

        binding.videoListFragmentError.buttonRepeatLoading.setOnClickListener {
            getVideoList()
        }
    }

    override fun onClick(video: Video) {
        val bundle = bundleOf(Constants.KEY_VIDEO_ID to video.id)
        findNavController().navigate(
            R.id.action_videoListFragment_to_videoPlayerFragment,
            bundle
        )
    }

    private fun setupRecyclerViewVideoListShimmer() {
        val videoShimmerAdapter = VideoShimmerAdapter(5)
        binding.recyclerViewVideoListShimmer.apply {
            adapter = videoShimmerAdapter
        }
    }

    private fun setupRecyclerViewVideoList() {
        videoAdapter = VideoAdapter(this)
        binding.recyclerViewVideoList.apply {
            adapter = videoAdapter
        }
    }

    private fun handleState(state: VideoListState) {
        when (state) {
            is VideoListState.Content -> handleContentState(state)
            is VideoListState.Error -> handleErrorState()
            is VideoListState.Refresh.Content -> handleRefreshContentState(state)
            is VideoListState.Refresh.Error -> handleRefreshErrorState(state)
            VideoListState.Loading -> handleLoadingState()
            VideoListState.Initial -> handleInitialState()
            else -> {}
        }
    }

    private fun handleContentState(state: VideoListState.Content) {
        videoAdapter?.submitList(state.videoList.items)

        binding.videoListFragmentContent.visibility = View.VISIBLE
        binding.videoListFragmentLoading.visibility = View.INVISIBLE
        stopShimmer()
    }

    private fun handleErrorState() {
        binding.swipeRefreshLayoutVideoList.isRefreshing = false
        binding.videoListFragmentContent.visibility = View.INVISIBLE
        binding.videoListFragmentLoading.visibility = View.INVISIBLE
        stopShimmer()
        binding.videoListFragmentError.root.visibility = View.VISIBLE
    }

    private fun handleRefreshContentState(state: VideoListState.Refresh.Content) {
        videoAdapter?.submitList(state.refreshVideoList.items)
        if (binding.swipeRefreshLayoutVideoList.isRefreshing) {
            binding.recyclerViewVideoList.scrollToPosition(0)
        }
        binding.swipeRefreshLayoutVideoList.isRefreshing = false
    }

    private fun handleRefreshErrorState(state: VideoListState.Refresh.Error) {
        videoAdapter?.submitList(state.oldVideoList)
        binding.swipeRefreshLayoutVideoList.isRefreshing = false
    }

    private fun handleLoadingState() {
        binding.videoListFragmentLoading.visibility = View.VISIBLE
        startShimmer()
        binding.videoListFragmentContent.visibility = View.INVISIBLE
        binding.videoListFragmentError.root.visibility = View.INVISIBLE
    }

    private fun handleInitialState() {
        getVideoList()
    }

    private fun startShimmer() {
        binding.shimmerFrameLayoutRecyclerViewVideoList.startShimmer()
    }

    private fun stopShimmer() {
        binding.shimmerFrameLayoutRecyclerViewVideoList.stopShimmer()
    }

    private fun getVideoList() {
        videoListViewModel.getVideoList()
    }

    private fun refreshVideoList() {
        videoListViewModel.refreshVideoList(videoAdapter?.currentList)
    }
}