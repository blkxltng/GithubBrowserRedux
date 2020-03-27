package com.blkxltng.githubbrowserredux.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blkxltng.githubbrowserredux.models.Organization
import com.blkxltng.githubbrowserredux.models.Repo
import com.blkxltng.githubbrowserredux.network.GithubService
import com.blkxltng.githubbrowserredux.utils.LiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel : ViewModel() {

    val selectedOrganization = MutableLiveData<Organization>()
    val listRepos = MutableLiveData<List<Repo>>()
    val repoClickedEvent = LiveEvent<Repo>()
    val searchQuery = MutableLiveData<String>()

    val progressVisibility = MutableLiveData(View.VISIBLE)

    val githubService: GithubService = GithubService()

    private fun loadOrganization(organizationName: String?) {

        val organizationResponse = githubService.getOrganization(organizationName!!)

        organizationResponse.enqueue(object: Callback<Organization> {
            override fun onResponse(call: Call<Organization>, response: Response<Organization>) {
                if (response.isSuccessful) {
                    progressVisibility.postValue(View.GONE)
                    selectedOrganization.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Organization>, t: Throwable) {
                progressVisibility.postValue(View.GONE)
                Timber.d(t, "error retrieving organization")
            }
        })
    }

    fun searchClicked() {
        progressVisibility.postValue(View.VISIBLE)
        loadOrganization(searchQuery.value)
    }
}