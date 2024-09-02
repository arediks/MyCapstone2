package com.example.mygithub.core.utils

import com.example.mygithub.core.data.source.local.entity.FavoriteEntity
import com.example.mygithub.core.data.source.local.entity.UserListEntity
import com.example.mygithub.core.data.source.remote.response.UserDetailResponse
import com.example.mygithub.core.data.source.remote.response.UserFollowResponse
import com.example.mygithub.core.data.source.remote.response.UserListResponse
import com.example.mygithub.core.domain.model.GitUserDetail
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.core.domain.model.GitUsers

object DataMapper {

    fun mapUserEntityToDomain(input: UserListEntity): GitUserList =
        input.let {
            GitUserList(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                isFavorite = it.isFavorite
            )
        }

    fun mapUserDetailResponseToDomain(input: UserDetailResponse): GitUserDetail =
        GitUserDetail(
            login = input.login,
            avatarUrl = input.avatarUrl,
            name = input.name ?: "",
            followers = input.followers,
            following = input.following
        )

    fun mapUserFollowResponseToDomain(input: List<UserFollowResponse>): List<GitUsers> =
        input.map {
            GitUsers(
                login = it.login,
                avatarUrl = it.avatarUrl
            )
        }

    fun mapFavoriteEntityToDomain(input: List<FavoriteEntity>): List<GitUserList> =
        input.map {
            GitUserList(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                isFavorite = true
            )
        }

    fun mapGitUserListToFavoriteEntity(input: GitUserList): FavoriteEntity =
        FavoriteEntity(
            id = input.id,
            login = input.login,
            avatarUrl = input.avatarUrl
        )

    fun mapGitUserLIstToUserListEntity(input: GitUserList): UserListEntity =
        UserListEntity(
            id = input.id,
            login = input.login,
            avatarUrl = input.avatarUrl,
            isFavorite = input.isFavorite
        )

    fun mapUserListResponseToEntity(input: List<UserListResponse>): List<UserListEntity> =
        input.map {
            UserListEntity(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                isFavorite = it.isFavorite
            )
        }
}