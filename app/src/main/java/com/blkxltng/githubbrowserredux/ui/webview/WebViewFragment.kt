package com.blkxltng.githubbrowserredux.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.blkxltng.githubbrowserredux.R
import com.blkxltng.githubbrowserredux.databinding.FragmentWebviewBinding
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
        loadUrl()
    }

    private fun setupObservers() {

    }

    private fun loadUrl() {
        webView.loadUrl(args.repoUrl)
    }
}