package com.blkxltng.githubbrowserredux.ui.main

import com.airbnb.epoxy.TypedEpoxyController
import com.blkxltng.githubbrowserredux.itemErrorMessage

class ErrorEpoxyController : TypedEpoxyController<String>() {

    override fun buildModels(errorMessage: String) {
        itemErrorMessage {
            id("errorMessage")
            message(errorMessage)
        }
    }
}