package com.blkxltng.githubbrowserredux.network

import com.blkxltng.githubbrowserredux.models.Organization
import com.blkxltng.githubbrowserredux.models.Repo
import com.blkxltng.githubbrowserredux.utils.GITHUB_API_BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GithubService {

    private val githubAPI: GithubAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        githubAPI = retrofit.create(GithubAPI::class.java)
    }

    fun getOrganizationRepos(organizationName: String): Call<List<Repo>> {
        return githubAPI.getOrganizationRepos(organizationName)
    }

    fun getOrganization(organizationName: String): Call<Organization> {
        return githubAPI.getOrganization(organizationName)
    }
}