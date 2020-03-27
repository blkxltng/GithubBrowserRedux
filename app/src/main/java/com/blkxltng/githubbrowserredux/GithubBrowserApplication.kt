package com.blkxltng.githubbrowserredux

import android.app.Application
import timber.log.Timber

class GithubBrowserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}