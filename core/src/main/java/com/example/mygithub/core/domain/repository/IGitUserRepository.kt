package com.example.mygithub.core.domain.repository

import androidx.paging.PagingData
import com.example.mygithub.core.data.Result
import com.example.mygithub.core.domain.model.GitUserDetail
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.core.domain.model.GitUsers
import kotlinx.coroutines.flow.Flow

interface IGitUserRepository {

    fun getUserList(): Flow<PagingData<GitUserList>>

    fun getUserSearch(username: String): Flow<PagingData<GitUserList>>

    fun getUserDetail(username: String): Flow<Result<GitUserDetail>>

    fun getUserFollowers(username: String): Flow<Result<List<GitUsers>>>

    fun getUserFollowing(username: String): Flow<Result<List<GitUsers>>>

    fun getUserFavorite(): Flow<Result<List<GitUserList>>>

    suspend fun insertFavorite(userFavorite: GitUserList)

    suspend fun deleteFavorite(userFavorite: GitUserList)

    fun getThemeSetting(): Flow<Boolean>

    suspend fun saveThemeSetting(isDarkMode: Boolean)

    fun getSearchSuggestion(query: String): Flow<List<String>>
}