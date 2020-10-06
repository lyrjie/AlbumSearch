package com.example.albumsearch.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsearch.databinding.TrackItemBinding
import com.example.albumsearch.model.network.dto.Album
import com.example.albumsearch.model.network.dto.LookupEntity
import com.example.albumsearch.view.adapters.AlbumAdapter.ViewHolder

/**
 * [ListAdapter] for [LookupEntity]
 */
class LookupEntityAdapter :
    ListAdapter<LookupEntity, LookupEntityAdapter.ViewHolder>(SongDiffCallback()) {

    /**
     * Item ViewHolder
     *
     * @property binding
     */
    class ViewHolder private constructor(private val binding: TrackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            /**
             * Returns [ViewHolder] inflated using passed [parent]
             *
             * @param parent
             * @return
             */
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TrackItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        /**
         * Updates [ViewHolder] with passed [lookupEntity]
         *
         * @param lookupEntity
         */
        fun bind(lookupEntity: LookupEntity) {
            binding.track = lookupEntity
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

/**
 * Naive [DiffUtil.ItemCallback] for [LookupEntity]
 */
class SongDiffCallback : DiffUtil.ItemCallback<LookupEntity>() {
    override fun areItemsTheSame(oldItem: LookupEntity, newItem: LookupEntity) = oldItem === newItem
    override fun areContentsTheSame(oldItem: LookupEntity, newItem: LookupEntity) =
        oldItem == newItem
}