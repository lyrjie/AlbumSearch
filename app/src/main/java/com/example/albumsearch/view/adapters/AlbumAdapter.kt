package com.example.albumsearch.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsearch.databinding.AlbumItemBinding
import com.example.albumsearch.model.database.entities.AlbumEntity

/**
 * [ListAdapter] for albums ([AlbumEntity])
 *
 * @property onClickListener onClick listener to wire up to entries
 */
class AlbumAdapter(private val onClickListener: AlbumOnClickListener) :
    ListAdapter<AlbumEntity, AlbumAdapter.ViewHolder>(AlbumDiffCallback()) {

    /**
     * Item ViewHolder
     */
    class ViewHolder private constructor(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            /**
             * Returns [ViewHolder] inflated using passed [parent]
             */
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AlbumItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        /**
         * Updates [ViewHolder] with passed [album]
         *
         * @param album
         */
        fun bind(album: AlbumEntity) {
            binding.album = album
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        // Wiring up our onClickListener
        holder.itemView.setOnClickListener { onClickListener.onClick(item) }
        holder.bind(item)
    }
}

/**
 * Simple [DiffUtil.ItemCallback] for [AlbumEntity]
 */
class AlbumDiffCallback : DiffUtil.ItemCallback<AlbumEntity>() {
    override fun areItemsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity) = oldItem == newItem
}

/**
 * Allows to bind onClick callbacks to [RecyclerView] items, which use the underlying data object
 *
 * @property clickListener
 */
class AlbumOnClickListener(val clickListener: (album: AlbumEntity) -> Unit) {
    fun onClick(album: AlbumEntity) = clickListener(album)
}