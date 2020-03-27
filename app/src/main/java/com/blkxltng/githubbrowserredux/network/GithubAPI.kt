package com.blkxltng.githubbrowserredux.network

import com.blkxltng.githubbrowserredux.models.Organization
import com.blkxltng.githubbrowserredux.models.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {

    @GET("orgs/{organization}/repos")
    fun getOrganizationRepos(@Path("organization") organizationName: String): Call<List<Repo>>

    @GET("/orgs/{organization}")
    fun getOrganization(@Path("organization") organizationName: String): Call<Organization>

}