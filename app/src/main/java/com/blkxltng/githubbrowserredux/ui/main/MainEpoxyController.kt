package com.blkxltng.githubbrowserredux.ui.main

import com.airbnb.epoxy.EpoxyController
import com.blkxltng.githubbrowserredux.itemOrganization
import com.blkxltng.githubbrowserredux.itemRepo
import com.blkxltng.githubbrowserredux.models.Organization
import com.blkxltng.githubbrowserredux.models.Repo

class MainEpoxyController(val organization: Organization, val repos: List<Repo>?) : EpoxyController() {
    override fun buildModels() {
        itemOrganization {
            id("organizationLayout")
            organization(organization)
        }
    }
}