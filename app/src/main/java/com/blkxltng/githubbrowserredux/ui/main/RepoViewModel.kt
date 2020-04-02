package com.blkxltng.githubbrowserredux.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blkxltng.githubbrowserredux.models.GitHubRepo

class RepoViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    val singleRepo = MutableLiveData<GitHubRepo>()

    fun repoClicked() {
        mainViewModel.repoClickedEvent.postValue(singleRepo.value)
    }
}