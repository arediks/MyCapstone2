package com.example.mygithub.core.data.source.remote

import android.util.Log
import com.example.mygithub.core.data.source.remote.response.SearchSuggestionResponse
import com.example.mygithub.core.data.source.remote.response.UserDetailResponse
import com.example.mygithub.core.data.source.remote.response.UserFollowResponse
import com.example.mygithub.core.data.source.remote.response.UserListResponse
import com.example.mygithub.core.data.source.remote.retrofit.ApiResponse
import com.example.mygithub.core.data.source.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun getUserList(since: Int, perPage: Int): Flow<ApiResponse<List<UserListResponse>>> = flow {
        try {
            val response = apiService.getUserList(since, perPage)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "getUserSearch: ${e.message.toString()}")
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    fun getUserSearch(username: String, since: Int, perPage: Int): Flow<ApiResponse<List<UserListResponse>>> = flow {
        try {
            if (since == 0) {
                val response = apiService.getUserSearch(username, since, perPage).items
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            Log.d(TAG, "getUserSearch: ${e.message.toString()}")
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    fun getUserDetail(username: String): Flow<ApiResponse<UserDetailResponse>> = flow {
        try {
            val response = apiService.getUserDetail(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "getUserDetail: ${e.message.toString()}")
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    fun getUserFollowers(username: String): Flow<ApiResponse<List<UserFollowResponse>>> = flow {
        try {
            val response = apiService.getUserFollowers(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "getUserFollowers: ${e.message.toString()}")
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    fun getUserFollowing(username: String): Flow<ApiResponse<List<UserFollowResponse>>> = flow {
        try {
            val response = apiService.getUserFollowing(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "getUserFollowers: ${e.message.toString()}")
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    fun getSearchSuggestion(query: String): Flow<List<SearchSuggestionResponse>> = flow {
        try {
            val response = apiService.getSearchSuggestion(query).items
            emit(response)
        } catch (e: Exception) {
            Log.d(TAG, "getSearchSuggestion: ${e.message.toString()}")
        }
    }

    companion object {
        private const val TAG = "RemoteDataSource"
    }
}