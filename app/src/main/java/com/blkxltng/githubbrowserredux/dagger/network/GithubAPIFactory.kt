package com.blkxltng.githubbrowserredux.dagger.network

import com.blkxltng.githubbrowserredux.network.GithubAPI
import com.blkxltng.githubbrowserredux.network.GithubService
import com.blkxltng.githubbrowserredux.ui.main.MainViewModel
import dagger.Component

@Component(modules = [GithubModule::class])
interface GitHubAPIFactory {
    fun provideGithubApi(): GithubAPI
    fun providesGithubService(): GithubService
    fun inject(mainViewModel: MainViewModel)
}