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

class MainViewModel : ViewModel() {

    val selectedOrganization = MutableLiveData<Organization>() // Used to hold the info for the organization
    val repoClickedEvent = LiveEvent<Repo>() // Called when the user clicks a repository
    val searchClickedEvent = LiveEvent<Void>() // Called when the user clicks the search button
    val searchQuery = MutableLiveData<String>() // Used to keep track of the user's edittext input
    val repoInfo = MutableLiveData<Pair<Organization?, List<Repo>?>>() // Holds information for the repos as well as the organization
    val progressVisibility = MutableLiveData(View.GONE) // Used to set progressBar visibility
    val errorCode = LiveEvent<GitHubErrorCode>() // Used when there is an error to send the user a message

    private val githubService: GithubService = GithubService()

    fun searchClicked() {
        searchClickedEvent.call()
    }

    fun continueSearch() {
        if (searchQuery.value?.trim().isNullOrEmpty()) {
            errorCode.postValue(GitHubErrorCode.NO_INPUT)
        } else {
            progressVisibility.postValue(View.VISIBLE)
            loadOrganization(searchQuery.value)
        }
    }

    private fun loadOrganization(organizationName: String?) {

        val organizationResponse = githubService.getOrganization(organizationName!!)

        organizationResponse.enqueue(object: Callback<Organization> {
            override fun onResponse(call: Call<Organization>, response: Response<Organization>) {
                if (response.isSuccessful) {
                    selectedOrganization.postValue(response.body())
                    loadRepos(organizationName)
                } else {
                    if(response.code() == 404) {
                        errorCode.postValue(GitHubErrorCode.NOT_FOUND)
                        progressVisibility.postValue(View.GONE)
                    }
                }
            }

            override fun onFailure(call: Call<Organization>, t: Throwable) {
                errorCode.postValue(GitHubErrorCode.ERROR_ORGANIZATION)
                progressVisibility.postValue(View.GONE)
            }
        })
    }

    private fun loadRepos(organizationName: String?) {

        val reposResponse = githubService.getOrganizationRepos(organizationName!!)

        reposResponse.enqueue(object: Callback<List<Repo>> {
            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                errorCode.postValue(GitHubErrorCode.ERROR_REPO)
                progressVisibility.postValue(View.GONE)
            }

            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                if (response.isSuccessful) {
                    progressVisibility.postValue(View.GONE)
                    val smallerList = response.body()?.sortedBy { it.stargazers_count }?.reversed()?.take(3)
                    repoInfo.postValue(Pair(selectedOrganization.value, smallerList))
                }
            }
        })
    }

    enum class GitHubErrorCode {
        NOT_FOUND, NO_CONNECTION, ERROR_ORGANIZATION, ERROR_REPO, NO_INPUT
    }
}