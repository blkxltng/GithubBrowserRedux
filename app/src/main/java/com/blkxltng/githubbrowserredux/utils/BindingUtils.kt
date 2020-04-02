package com.blkxltng.githubbrowserredux.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.blkxltng.githubbrowserredux.R
import com.bumptech.glide.Glide

// BindingAdapter that ImageViews will use to load the avatars for the organization
@BindingAdapter("organizationImageUrl")
fun ImageView.organizationImageUrl(imageUrl: String?) {
    if(imageUrl != null) {
        Glide.with(this.context).load(imageUrl).fitCenter().into(this)
    }
}

@BindingAdapter("numCountText")
fun TextView.numCountText(count: Int) {
    text = count.toString()
}

@BindingAdapter("descriptionText")
fun TextView.descriptionText(description: String?) {
    if(!description.isNullOrEmpty()) {
        text = description
    } else {
        text = this.context.getString(R.string.error_no_description)
    }
}

@BindingAdapter("locationText")
fun TextView.locationText(location: String?) {
    if(location.isNullOrEmpty()) {
        this.visibility = View.GONE
    } else {
        text = this.context.getString(R.string.org_location, location)
    }
}