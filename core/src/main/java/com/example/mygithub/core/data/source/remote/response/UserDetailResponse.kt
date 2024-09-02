package com.example.mygithub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int
)