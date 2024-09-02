package com.example.mygithub.core.data.source.remote.retrofit

import com.example.mygithub.core.data.source.remote.response.SearchSuggestionItemsResponse
import com.example.mygithub.core.data.source.remote.response.UserDetailResponse
import com.example.mygithub.core.data.source.remote.response.UserFollowResponse
import com.example.mygithub.core.data.source.remote.response.UserListResponse
import com.example.mygithub.core.data.source.remote.response.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUserList(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<UserListResponse>

    @GET("users/{login}")
    suspend fun getUserDetail(
        @Path("login") id: String
    ): UserDetailResponse

    @GET("users/{login}/followers")
    suspend fun getUserFollowers(
        @Path("login") id: String
    ): List<UserFollowResponse>

    @GET("users/{login}/following")
    suspend fun getUserFollowing(
        @Path("login") id: String
    ): List<UserFollowResponse>

    @GET("search/users")
    suspend fun getUserSearch(
        @Query("q") id: String,
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): UserSearchResponse

    @GET("search/users")
    suspend fun getSearchSuggestion(
        @Query("q") query: String
    ): SearchSuggestionItemsResponse
}