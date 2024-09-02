package com.example.mygithub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(

    @SerializedName("items")
    val items: List<UserListResponse>
)