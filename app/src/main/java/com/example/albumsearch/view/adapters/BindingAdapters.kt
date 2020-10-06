package com.example.albumsearch.view.adapters

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.albumsearch.R

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
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}