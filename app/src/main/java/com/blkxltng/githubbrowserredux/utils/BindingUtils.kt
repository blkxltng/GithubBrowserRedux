package com.blkxltng.githubbrowserredux.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// BindingAdapter that ImageViews will use to load the avatars for the organization
@BindingAdapter("organizationImageUrl")
fun ImageView.orgnaizationImageUrl(imageUrl: String?) {
    if(imageUrl != null) {
        Glide.with(this.context).load(imageUrl).fitCenter().into(this)
    }
}

@BindingAdapter("numCountText")
fun TextView.numCountText(count: Int) {
    text = count.toString()
}

@BindingAdapter("repoDescription")
fun TextView.repoDescription(description: String?) {
    if(description != null) {
        text = description
    } else {
        text = "-- no description found --"
    }
}