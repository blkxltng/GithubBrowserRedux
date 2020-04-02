package com.blkxltng.githubbrowserredux.models

class GitHubOrganization (
    val id: Int,
    val login: String,
    val repos_url: String,
    val avatar_url: String,
    val description: String,
    val name: String,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val is_verified: Boolean,
    val html_url: String,
    val created_at: String,
    val updated_at: String
)