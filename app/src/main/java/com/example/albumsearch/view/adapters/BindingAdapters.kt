package com.example.albumsearch.view.adapters

import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
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

/**
 * Sets [TextView]'s text to [newText] with dot added in the end
 *
 * @param newText
 */
@BindingAdapter("numberWithDot")
fun TextView.setNumberWithDot(newText: Int?) {
    newText?.let {
        text = context.getString(R.string.number_with_dot, newText)
    }
}

/**
 * Sets [TextView]'s text to [millis] converted to readable duration (1:23:45)
 *
 * @param millis
 */
@BindingAdapter("durationText")
fun TextView.setDurationText(millis: Long?) {
    millis?.let {
        text = DateUtils.formatElapsedTime(millis / 1000)
    }
}