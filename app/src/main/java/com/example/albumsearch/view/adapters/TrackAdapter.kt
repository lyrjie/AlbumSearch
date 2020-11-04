package com.example.albumsearch.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsearch.databinding.TrackItemBinding
import com.example.albumsearch.model.database.entities.TrackEntity

/** [ListAdapter] for [TrackEntity] */
class TrackAdapter :
    ListAdapter<TrackEntity, TrackAdapter.ViewHolder>(TrackDiffCallback()) {

    /** Item ViewHolder */
    class ViewHolder private constructor(private val binding: TrackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            /** Returns [ViewHolder] inflated using passed [parent] */
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TrackItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        /** Updates [ViewHolder] with passed [track] */
        fun bind(track: TrackEntity) {
            binding.track = track
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

/** Simple [DiffUtil.ItemCallback] for [TrackEntity] */
class TrackDiffCallback : DiffUtil.ItemCallback<TrackEntity>() {
    override fun areItemsTheSame(oldItem: TrackEntity, newItem: TrackEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TrackEntity, newItem: TrackEntity) = oldItem == newItem
}