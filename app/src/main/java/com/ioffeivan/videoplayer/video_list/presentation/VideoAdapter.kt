package com.ioffeivan.videoplayer.video_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.ioffeivan.videoplayer.video.domain.model.Video
import com.ioffeivan.videoplayer.databinding.ItemVideoBinding

class VideoAdapter(
    private val listener: Listener,
) : ListAdapter<Video, VideoAdapter.VideoViewHolder>(VideoCallback()) {

    class VideoViewHolder(
        private val binding: ItemVideoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video) {
            binding.imageViewVideoThumbnail.load(video.imageUrl)
            binding.textViewVideoDuration.text = video.formattedDuration
            binding.textViewVideoName.text = video.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = getItem(position)
        holder.bind(video)
        holder.itemView.setOnClickListener {
            listener.onClick(video)
        }
    }

    class VideoCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun onClick(video: Video)
    }
}