package com.example.mygithub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserFollowResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)