package com.blkxltng.githubbrowserredux.models

class Repo (
    val id: Int,
    val name: String,
    val private: Boolean,
    val html_url: String,
    val description: String?,
    val created_at: String,
    val updated_at: String,
    val pushed_at: String,
    val stargazers_count: Int,
    val watchers_count: Int,
    val default_branch: String
)