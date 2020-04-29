package com.blkxltng.githubbrowserredux.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blkxltng.githubbrowserredux.dagger.network.DaggerGitHubAPIFactory
import com.blkxltng.githubbrowserredux.models.GitHubOrganization
import com.blkxltng.githubbrowserredux.models.GitHubRepo
import com.blkxltng.githubbrowserredux.network.GithubService
import com.blkxltng.githubbrowserredux.utils.LiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

class MainViewModel : ViewModel() {

    val repoClickedEvent = LiveEvent<GitHubRepo>() // Called when the user clicks a repository
    val searchClickedEvent = LiveEvent<Void>() // Called when the user clicks the search button
    val searchQuery = MutableLiveData<String>() // Used to keep track of the user's edittext input
    val repoInfo = MutableLiveData<Pair<GitHubOrganization?, List<GitHubRepo>?>?>() // Holds information for the repos as well as the organization
    val progressVisibility = MutableLiveData(View.GONE) // Used to set progressBar visibility
    val errorCode = LiveEvent<GitHubErrorCode>() // Used when there is an error to send the user a message

    @Inject
    @Singleton
    lateinit var githubService: GithubService

    init {
        DaggerGitHubAPIFactory.create().inject(this)
    }

    private var foundOrganization: GitHubOrganization? = null

    fun searchClicked() {
        searchClickedEvent.call()
    }

    fun continueSearch() {
        if (searchQuery.value?.trim().isNullOrEmpty()) {
            errorCode.postValue(GitHubErrorCode.NO_INPUT)
        } else {
            progressVisibility.postValue(View.VISIBLE)
//            loadOrganization(searchQuery.value)
            loadOrganizationReactive(searchQuery.value)
        }
    }

//    private fun loadOrganization(organizationName: String?) {
//
//        val organizationResponse = githubService.getOrganization(organizationName!!)
//
//        organizationResponse.enqueue(object: Callback<GitHubOrganization> {
//            override fun onResponse(call: Call<GitHubOrganization>, response: Response<GitHubOrganization>) {
//                if (response.isSuccessful) {
//                    foundOrganization = response.body()
//                    loadRepos(organizationName)
//                } else {
//                    if(response.code() == 404) {
//                        errorCode.postValue(GitHubErrorCode.NOT_FOUND)
//                        progressVisibility.postValue(View.GONE)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<GitHubOrganization>, t: Throwable) {
//                errorCode.postValue(GitHubErrorCode.ERROR_ORGANIZATION)
//                progressVisibility.postValue(View.GONE)
//                Timber.d(t)
//            }
//        })
//    }

    private fun loadOrganizationReactive(organizationName: String?) {
        val organizationResponse = githubService.getOrganizationReactive(organizationName)

        organizationResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ organization ->
                foundOrganization = organization
                loadReposReactive(organizationName)
            }, { error ->
                if(error.message.equals("HTTP 404 Not Found")) {
                    errorCode.postValue(GitHubErrorCode.NOT_FOUND)
                } else {
                    errorCode.postValue(GitHubErrorCode.ERROR_REPO)
                }
                progressVisibility.postValue(View.GONE)
                Timber.d(error)
            })

    }

//    private fun loadRepos(organizationName: String?) {
//
//        val reposResponse = githubService.getOrganizationRepos(organizationName!!)
//
//        reposResponse.enqueue(object: Callback<List<GitHubRepo>> {
//            override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
//                errorCode.postValue(GitHubErrorCode.ERROR_REPO)
//                progressVisibility.postValue(View.GONE)
//                Timber.d(t)
//            }
//
//            override fun onResponse(call: Call<List<GitHubRepo>>, response: Response<List<GitHubRepo>>) {
//                if (response.isSuccessful) {
//                    progressVisibility.postValue(View.GONE)
//                    val smallerList = response.body()?.sortedBy { it.stargazers_count }?.reversed()?.take(3)
//                    repoInfo.postValue(Pair(foundOrganization, smallerList))
//                }
//            }
//        })
//    }

    private fun loadReposReactive(organizationName: String?) {

        val reposResponse = githubService.getOrganizationReposReactive(organizationName)

        reposResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ repoList ->
                progressVisibility.postValue(View.GONE)
                val smallerList = repoList.sortedBy { it.stargazers_count }.reversed().take(3)
                repoInfo.postValue(Pair(foundOrganization, smallerList))
            }, { error ->
                errorCode.postValue(GitHubErrorCode.ERROR_REPO)
                progressVisibility.postValue(View.GONE)
                Timber.d(error)
            })

            }

    enum class GitHubErrorCode {
        NOT_FOUND, NO_CONNECTION, ERROR_ORGANIZATION, ERROR_REPO, NO_INPUT
    }
}