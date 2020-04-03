package com.blkxltng.githubbrowserredux.dagger.network

import com.blkxltng.githubbrowserredux.network.GithubAPI
import com.blkxltng.githubbrowserredux.utils.GITHUB_API_BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object GithubModule {
    @Provides
    @JvmStatic
    fun gitApi() = Retrofit.Builder().baseUrl(GITHUB_API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(GithubAPI::class.java)
}