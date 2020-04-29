package com.blkxltng.githubbrowserredux.network

import com.blkxltng.githubbrowserredux.models.GitHubOrganization
import com.blkxltng.githubbrowserredux.models.GitHubRepo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GithubService @Inject constructor(private val githubAPI: GithubAPI) {
//
//    fun getOrganizationRepos(organizationName: String): Call<List<GitHubRepo>> {
//        return githubAPI.getOrganizationRepos(organizationName)
//    }
//
//    fun getOrganization(organizationName: String): Call<GitHubOrganization> {
//        return githubAPI.getOrganization(organizationName)
//    }

    fun getOrganizationReactive(organizationName: String?): Observable<GitHubOrganization> {
        return githubAPI.getOrganizationReactive(organizationName)
    }

    fun getOrganizationReposReactive(organizationName: String?): Observable<List<GitHubRepo>> {
        return githubAPI.getOrganizationReposReactive(organizationName)
    }
}