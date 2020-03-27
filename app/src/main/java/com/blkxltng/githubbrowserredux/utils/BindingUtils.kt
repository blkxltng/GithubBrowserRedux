package com.blkxltng.githubbrowserredux.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// BindingAdapter that ImageViews will use to load the avatars for the organization
@BindingAdapter("organizationImageUrl")
fun ImageView.orgnaizationImageUrl(imageUrl: String?) {
    if(imageUrl != null) {
        Glide.with(this.context).load(imageUrl).fitCenter().into(this)
    }
}