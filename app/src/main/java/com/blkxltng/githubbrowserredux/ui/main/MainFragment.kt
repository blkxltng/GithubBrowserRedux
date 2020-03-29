package com.blkxltng.githubbrowserredux.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blkxltng.githubbrowserredux.R
import com.blkxltng.githubbrowserredux.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var positionIndex: Int = -1
    private var topView: Int = -1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mainViewModel = viewModel
        binding.executePendingBindings()
        setupObservers()
    }

//    override fun onPause() {
//        super.onPause()
//
//        // Save the scroll position of the recyclerView onPause()
//        positionIndex = layoutManager.findFirstVisibleItemPosition()
//        val startView: View = recyclerView.getChildAt(0)
//        topView = startView.top - recyclerView.paddingTop
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        // Restore the scroll position of the recyclerView onResume()
//        if (positionIndex != -1) {
//            layoutManager.scrollToPositionWithOffset(positionIndex, topView)
//        }
//    }

    private fun setupObservers() {

        viewModel.testObject.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "You found the organization ${it.first?.name}", Toast.LENGTH_SHORT).show()
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val repoList = mutableListOf<RepoViewModel>()
            it.second?.forEach { repoList.add(RepoViewModel(viewModel).apply { singleRepo.postValue(it) }) }
            recyclerView.setControllerAndBuildModels(MainEpoxyController(organization = it.first!!, repos = repoList))
        })

        viewModel.repoClickedEvent.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "You clicked ${it.name}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToWebViewFragment(it.html_url))
        })
    }

}