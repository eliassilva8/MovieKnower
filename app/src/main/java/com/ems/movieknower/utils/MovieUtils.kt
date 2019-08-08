package com.ems.movieknower.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

private val image_base_url = "http://image.tmdb.org/t/p/"

fun loadPoster(view: ImageView, path: String?, imageSize: String) {
    Glide
        .with(view.context)
        .load(image_base_url + imageSize + path)
        .into(view)
}