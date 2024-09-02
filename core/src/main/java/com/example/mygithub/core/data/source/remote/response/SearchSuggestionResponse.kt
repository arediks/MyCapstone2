package com.example.mygithub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchSuggestionResponse(
    @SerializedName("login")
    val login: String
)