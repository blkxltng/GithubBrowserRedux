package com.blkxltng.githubbrowserredux.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.blkxltng.githubbrowserredux.R
import com.blkxltng.githubbrowserredux.databinding.FragmentWebviewBinding
import com.blkxltng.githubbrowserredux.utils.isNetworkConnected
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_webview.*

class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebviewBinding
    private val viewModel: WebViewViewModel by viewModels()
    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_webview, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.webViewViewModel = viewModel
        binding.executePendingBindings()
        setupObservers()
        checkConnected()
    }

    private fun setupObservers() {
        viewModel.isNetworkConnected.observe(viewLifecycleOwner, Observer {connected ->
            if(connected) {
                loadUrl()
            }
        })
    }

    private fun loadUrl() {
        viewModel.progressVisibility.postValue(View.VISIBLE)
        webView.loadUrl(args.repoUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                viewModel.progressVisibility.postValue(View.GONE)
            }
        }
    }

    private fun showSnackbar() {
        val connSnackbar = Snackbar.make(binding.root, R.string.error_no_connection, Snackbar.LENGTH_INDEFINITE)
        connSnackbar.setAction(R.string.retry) {
            connSnackbar.dismiss()
            if (isNetworkConnected()) {
                viewModel.isNetworkConnected.postValue(true)
            } else {
                showSnackbar()
            }
        }
        connSnackbar.show()
    }

    private fun checkConnected() {
        if(isNetworkConnected()) {
            viewModel.isNetworkConnected.postValue(true)
        } else {
            showSnackbar()
        }
    }

}