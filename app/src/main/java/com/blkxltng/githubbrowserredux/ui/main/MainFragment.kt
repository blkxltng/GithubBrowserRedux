package com.blkxltng.githubbrowserredux.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blkxltng.githubbrowserredux.R
import com.blkxltng.githubbrowserredux.databinding.FragmentMainBinding
import com.blkxltng.githubbrowserredux.utils.hideKeyboard
import com.blkxltng.githubbrowserredux.utils.isNetworkConnected
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private  lateinit var layoutManager: LinearLayoutManager

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var positionIndex: Int = -1
    private var topView: Int = -1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mainViewModel = viewModel
        binding.executePendingBindings()
        setupObservers()
    }

    override fun onPause() {
        super.onPause()

        if(recyclerView.getChildAt(0) != null) {
            // Save the scroll position of the recyclerView onPause()
            positionIndex = layoutManager.findFirstVisibleItemPosition()
            val startView: View = recyclerView.getChildAt(0)
            topView = startView.top - recyclerView.paddingTop
        }
    }

    override fun onResume() {
        super.onResume()

        // Restore the scroll position of the recyclerView onResume()
        if (positionIndex != -1) {
            layoutManager.scrollToPositionWithOffset(positionIndex, topView)
        }

        // Add support search for IME option
        orgEditText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchClicked()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun setupObservers() {

        viewModel.searchClickedEvent.observe(viewLifecycleOwner, Observer {
            recyclerView.clear()
            if(isNetworkConnected()) {
                hideKeyboard()
                viewModel.continueSearch()
            } else {
                viewModel.errorCode.postValue(MainViewModel.GitHubErrorCode.NO_CONNECTION)
            }
        })

        viewModel.repoInfo.observe(viewLifecycleOwner, Observer { repoInfo ->
            recyclerView.layoutManager = layoutManager
            val repoList = mutableListOf<RepoViewModel>()
            repoInfo?.second?.forEach { repoList.add(RepoViewModel(viewModel).apply { singleRepo.value = it }) }

            val mainEpoxyController = MainEpoxyController()
            mainEpoxyController.setData(repoInfo?.first, repoList)
            recyclerView.setController(mainEpoxyController)
        })

        viewModel.repoClickedEvent.observe(viewLifecycleOwner, Observer { repo ->
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToWebViewFragment(repo.html_url))
        })

        viewModel.errorCode.observe(viewLifecycleOwner, Observer {error ->
            val message = when(error) {
                MainViewModel.GitHubErrorCode.ERROR_REPO -> context?.getString(R.string.error_repo)
                MainViewModel.GitHubErrorCode.ERROR_ORGANIZATION -> context?.getString(R.string.error_org)
                MainViewModel.GitHubErrorCode.NO_CONNECTION -> context?.getString(R.string.error_no_connection)
                MainViewModel.GitHubErrorCode.NOT_FOUND -> context?.getString(R.string.error_org_not_found, viewModel.searchQuery.value)
                MainViewModel.GitHubErrorCode.NO_INPUT -> context?.getString(R.string.error_no_input)
                else -> context?.getString(R.string.error_generic)
            }

            val errorEpoxyController = ErrorEpoxyController()
            errorEpoxyController.setData(message)
            recyclerView.setController(errorEpoxyController)
        })
    }

}