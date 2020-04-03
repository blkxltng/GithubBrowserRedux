package com.blkxltng.githubbrowserredux.ui.webview

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewViewModel : ViewModel() {
    val isNetworkConnected = MutableLiveData<Boolean>(false)
    val progressVisibility = MutableLiveData(View.GONE) // Used to set progressBar visibility
}