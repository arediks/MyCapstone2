package com.example.mygithub.core.domain.usecase

import androidx.paging.PagingData
import com.example.mygithub.core.data.Result
import com.example.mygithub.core.domain.model.GitUserDetail
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.core.domain.model.GitUsers
import com.example.mygithub.core.domain.repository.IGitUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitUserInteractor @Inject constructor(private val gitUserRepository: IGitUserRepository) : GitUserUseCase {

    override fun getUserList(): Flow<PagingData<GitUserList>> = gitUserRepository.getUserList()

    override fun getUserSearch(username: String): Flow<PagingData<GitUserList>> = gitUserRepository.getUserSearch(username)

    override fun getUserDetail(username: String): Flow<Result<GitUserDetail>> = gitUserRepository.getUserDetail(username)

    override fun getUserFollowers(username: String): Flow<Result<List<GitUsers>>> = gitUserRepository.getUserFollowers(username)

    override fun getUserFollowing(username: String): Flow<Result<List<GitUsers>>> = gitUserRepository.getUserFollowing(username)

    override fun getUserFavorite(): Flow<Result<List<GitUserList>>> = gitUserRepository.getUserFavorite()

    override suspend fun insertFavorite(userFavorite: GitUserList) = gitUserRepository.insertFavorite(userFavorite)

    override suspend fun deleteFavorite(userFavorite: GitUserList) = gitUserRepository.deleteFavorite(userFavorite)

    override fun getThemeSetting(): Flow<Boolean> = gitUserRepository.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkMode: Boolean) = gitUserRepository.saveThemeSetting(isDarkMode)

    override fun getSearchSuggestion(query: String): Flow<List<String>> = gitUserRepository.getSearchSuggestion(query)

}