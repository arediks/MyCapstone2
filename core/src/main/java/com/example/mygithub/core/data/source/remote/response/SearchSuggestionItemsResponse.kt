package com.example.mygithub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchSuggestionItemsResponse(
    @SerializedName("items")
    val items: List<SearchSuggestionResponse>
)