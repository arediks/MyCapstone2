package com.example.mygithub.core.domain.model

data class GitUserDetail(
    val login: String,
    val avatarUrl: String,
    val name: String,
    val followers: Int,
    val following: Int
)