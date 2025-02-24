package com.ioffeivan.videoplayer.video_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ioffeivan.videoplayer.R

class VideoShimmerAdapter(
    private val itemCount: Int,
): RecyclerView.Adapter<VideoShimmerAdapter.VideoShimmerViewHolder>() {

    class VideoShimmerViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoShimmerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_shimmer, parent, false)
        return VideoShimmerViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoShimmerViewHolder, position: Int) {}

    override fun getItemCount() = itemCount
}