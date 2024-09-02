package com.example.mygithub.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserListResponse(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    var isFavorite: Boolean = false
) : Parcelable