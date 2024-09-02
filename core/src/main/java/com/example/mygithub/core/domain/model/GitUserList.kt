package com.example.mygithub.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitUserList(
    val id: String,
    val login: String,
    val avatarUrl: String,
    var isFavorite: Boolean = false
) : Parcelable