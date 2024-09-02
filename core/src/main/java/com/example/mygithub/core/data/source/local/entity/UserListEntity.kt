package com.example.mygithub.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserListEntity(

    @field:ColumnInfo("id")
    @field:PrimaryKey
    var id: String,

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @field:ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean
)