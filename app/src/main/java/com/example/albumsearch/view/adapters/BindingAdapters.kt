package com.example.albumsearch.view.adapters

import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.albumsearch.R
import com.example.albumsearch.model.network.ApiStatus
import java.text.SimpleDateFormat
import java.util.*

/** Loads the image into [ImageView] using passed [url] */
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

/** Sets [TextView]'s text to [newText] with dot added in the end */
@BindingAdapter("numberWithDot")
fun TextView.setNumberWithDot(newText: Int?) {
    newText?.let {
        text = context.getString(R.string.number_with_dot, newText)
    }
}

/** Sets [TextView]'s text to [millis] converted to readable duration (1:23:45) */
@BindingAdapter("durationText")
fun TextView.setDurationText(millis: Long?) {
    millis?.let {
        text = DateUtils.formatElapsedTime(millis / 1000)
    }
}

/** Sets [TextView]'s text to complete years in [date] */
@BindingAdapter("yearText")
fun TextView.setYearText(date: Date?) {
    date?.let {
        // We convert to GregorianCalendar first, since Date.getYear() is deprecated
        val calendar = GregorianCalendar.getInstance()
        calendar.time = date
        text = calendar.get(Calendar.YEAR).toString()
    }
}

/** Sets [TextView]'s text to user's local date representation of [Date] */
@BindingAdapter("dateText")
fun TextView.setDateText(date: Date?) {
    date?.let {
        text = SimpleDateFormat.getDateInstance().format(date)
    }
}

/**
 * Changes [ImageView]'s drawable and visibility based on the passed [status].
 * [ApiStatus.ERROR] and [ApiStatus.LOADING] show fitting images while [ApiStatus.DONE] hides the
 * [ImageView]
 */
@BindingAdapter("statusImage")
fun ImageView.setStatusImage(status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            setImageResource(R.drawable.loading_animation)
            visibility = View.VISIBLE
        }
        ApiStatus.DONE -> {
            visibility = View.GONE
        }
        ApiStatus.ERROR -> {
            setImageResource(R.drawable.ic_network_error)
            visibility = View.VISIBLE
        }
    }
}