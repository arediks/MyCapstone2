package com.example.mygithub.core.data.source.paging3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.mygithub.core.data.source.local.LocalDataSource
import com.example.mygithub.core.data.source.local.entity.RemoteKeys
import com.example.mygithub.core.data.source.local.entity.UserListEntity
import com.example.mygithub.core.data.source.remote.RemoteDataSource
import com.example.mygithub.core.data.source.remote.retrofit.ApiResponse
import com.example.mygithub.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
class UserListRemoteMediator(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val search: String?
) : RemoteMediator<Int, UserListEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserListEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                val remoteIndex = remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
                val sinceIndex =
                    if (remoteKeys?.nextKey?.minus(1) == null) 0
                    else getSinceNext((remoteIndex - 1).toString()).toInt()

                listOf(remoteIndex, sinceIndex)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                val sinceIndex = getSincePrev(prevKey.toString()).toInt()
                listOf(prevKey, sinceIndex)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                val sinceIndex = getSinceNext(nextKey.toString()).toInt()
                listOf(nextKey, sinceIndex)
            }
        }

        try {
            val responseData = if (search == null) {
                remoteDataSource.getUserList(page[1], state.config.pageSize).single()
            } else {
                remoteDataSource.getUserSearch(search, page[1], state.config.pageSize).single()
            }
            var endOfPaginationReached = !search.isNullOrBlank()
            var userData: List<UserListEntity> = emptyList()

            when (responseData) {
                is ApiResponse.Empty -> endOfPaginationReached = true
                is ApiResponse.Error -> error(responseData.errorMessage)
                is ApiResponse.Success -> {
                    val data = responseData.data
                    data.forEach { user ->
                        val isFavorite = localDataSource.isFavorite(user.login)
                        user.isFavorite = isFavorite
                    }
                    userData = DataMapper.mapUserListResponseToEntity(data)
                }
            }


            val testing = CoroutineScope(Dispatchers.IO).launch {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteRemoteKeys()
                    localDataSource.deleteUser()
                }
                val prevKey = if (page[0] == 1) null else page[0] - 1
                val nextKey = if (endOfPaginationReached) null else page[0] + 1
                val keys = userData.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                localDataSource.insertAllRemoteKeys(keys)
                localDataSource.insertUser(userData)
            }
            testing.join()
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserListEntity>): RemoteKeys? {
        return state.pages.lastOrNull{ it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserListEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserListEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeysId(id)
            }
        }
    }

    private suspend fun getSincePrev(id: String): String {
        return localDataSource.getSince(id).first()
    }

    private suspend fun getSinceNext(id: String): String {
        return localDataSource.getSince(id).last()
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}