package com.example.albumsearch.view.adapters

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Loads the image into [ImageView] using passed [url]
 *
 * @param url
 */
@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    url?.let {
        // Convert to URI
        val uri = url
            .toUri()
            .buildUpon()
            .scheme("https")
            .build()

        // Load
        Glide.with(this.context)
            .load(uri)
            .into(this)
    }
}