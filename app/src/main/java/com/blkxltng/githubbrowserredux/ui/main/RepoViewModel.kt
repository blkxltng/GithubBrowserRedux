package com.blkxltng.githubbrowserredux.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blkxltng.githubbrowserredux.models.Repo

class RepoViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    val singleRepo = MutableLiveData<Repo>()

    fun repoClicked() {
        mainViewModel.repoClickedEvent.postValue(singleRepo.value)
    }
}