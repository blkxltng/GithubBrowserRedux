package com.blkxltng.githubbrowserredux.ui.main

import com.airbnb.epoxy.Typed2EpoxyController
import com.blkxltng.githubbrowserredux.itemOrganization
import com.blkxltng.githubbrowserredux.itemRepo
import com.blkxltng.githubbrowserredux.models.GitHubOrganization

class MainEpoxyController : Typed2EpoxyController<GitHubOrganization?, List<RepoViewModel>?>() {

    override fun buildModels(gitHubOrganization: GitHubOrganization?, repos: List<RepoViewModel>?) {

        itemOrganization {
            id("organizationLayout")
            organization(gitHubOrganization)
        }

        repos?.forEach {repoVM ->
            itemRepo {
                id("repo${repoVM.singleRepo.value?.id}")
                repoViewModel(repoVM)
            }
        }
    }

}