package com.blkxltng.githubbrowserredux.network

import com.blkxltng.githubbrowserredux.models.GitHubOrganization
import com.blkxltng.githubbrowserredux.models.GitHubRepo
import retrofit2.Call
import javax.inject.Inject

class GithubService @Inject constructor(private val githubAPI: GithubAPI) {

    fun getOrganizationRepos(organizationName: String): Call<List<GitHubRepo>> {
        return githubAPI.getOrganizationRepos(organizationName)
    }

    fun getOrganization(organizationName: String): Call<GitHubOrganization> {
        return githubAPI.getOrganization(organizationName)
    }
}