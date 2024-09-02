package com.example.mygithub.core.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mygithub.core.data.source.local.LocalDataSource
import com.example.mygithub.core.data.source.paging3.UserListRemoteMediator
import com.example.mygithub.core.data.source.remote.RemoteDataSource
import com.example.mygithub.core.data.source.remote.retrofit.ApiResponse
import com.example.mygithub.core.domain.model.GitUserDetail
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.core.domain.model.GitUsers
import com.example.mygithub.core.domain.repository.IGitUserRepository
import com.example.mygithub.core.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGitUserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUserList(): Flow<PagingData<GitUserList>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = UserListRemoteMediator(remoteDataSource, localDataSource, null),
            pagingSourceFactory = {
                localDataSource.getUserList()
            }
        ).flow.map { data ->
            data.map { DataMapper.mapUserEntityToDomain(it) }
        }
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun getUserSearch(username: String): Flow<PagingData<GitUserList>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30
            ),
            remoteMediator = UserListRemoteMediator(remoteDataSource, localDataSource, username),
            pagingSourceFactory = {
                localDataSource.getUserList()
            }
        ).flow.map { data ->
            data.map { DataMapper.mapUserEntityToDomain(it) }
        }
    }

    override fun getUserDetail(username: String): Flow<Result<GitUserDetail>> = flow {
        emit(Result.Loading)
        when (val response = remoteDataSource.getUserDetail(username).single()) {
            is ApiResponse.Empty -> {
                emit(Result.Error("Data user tidak ditemukan"))
            }

            is ApiResponse.Error -> {
                val error = response.errorMessage
                emit(Result.Error(error))
            }

            is ApiResponse.Success -> {
                val data = response.data
                emit(Result.Success(DataMapper.mapUserDetailResponseToDomain(data)))
            }
        }
    }

    override fun getUserFollowers(username: String): Flow<Result<List<GitUsers>>> = flow {
        emit(Result.Loading)
        when (val response = remoteDataSource.getUserFollowers(username).single()) {
            is ApiResponse.Empty -> {
                emit(Result.Success(emptyList()))
            }

            is ApiResponse.Error -> {
                val error = response.errorMessage
                emit(Result.Error(error))
            }

            is ApiResponse.Success -> {
                val data = response.data
                emit(Result.Success(DataMapper.mapUserFollowResponseToDomain(data)))
            }
        }
    }

    override fun getUserFollowing(username: String): Flow<Result<List<GitUsers>>> = flow {
        emit(Result.Loading)
        when (val response = remoteDataSource.getUserFollowing(username).single()) {
            is ApiResponse.Empty -> {
                emit(Result.Success(emptyList()))
            }

            is ApiResponse.Error -> {
                val error = response.errorMessage
                emit(Result.Error(error))
            }

            is ApiResponse.Success -> {
                val data = response.data
                emit(Result.Success(DataMapper.mapUserFollowResponseToDomain(data)))
            }
        }
    }

    override fun getUserFavorite(): Flow<Result<List<GitUserList>>> = flow {
        emit(Result.Loading)
            val userFavorite = localDataSource.getUserFavorite().map {
                Result.Success(DataMapper.mapFavoriteEntityToDomain(it))
            }
            emitAll(userFavorite)
    }.catch {
        Log.d(TAG, "getUserFavorite: ${it.message.toString()}")
        emit(Result.Error(it.message.toString()))
    }

    override suspend fun insertFavorite(userFavorite: GitUserList) {
        localDataSource.insertFavorite(DataMapper.mapGitUserListToFavoriteEntity(userFavorite))
        localDataSource.updateUser(DataMapper.mapGitUserLIstToUserListEntity(userFavorite))
    }

    override suspend fun deleteFavorite(userFavorite: GitUserList) {
        localDataSource.deleteFavorite(DataMapper.mapGitUserListToFavoriteEntity(userFavorite))
        localDataSource.updateUser(DataMapper.mapGitUserLIstToUserListEntity(userFavorite))
    }

    override fun getThemeSetting(): Flow<Boolean> {
        return localDataSource.getThemeSetting()
    }

    override suspend fun saveThemeSetting(isDarkMode: Boolean) {
        localDataSource.saveThemeSetting(isDarkMode)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSearchSuggestion(query: String): Flow<List<String>> =
        remoteDataSource.getSearchSuggestion(query).mapLatest { response ->
            response.map {
                it.login
            }.filter {
                it.contains(query)
            }.take(5)
        }

    companion object {
        private const val TAG = "repository"
    }
}