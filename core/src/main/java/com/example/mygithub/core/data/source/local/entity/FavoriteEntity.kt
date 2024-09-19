package com.example.mygithub.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteEntity(
    @field:ColumnInfo(name = "fid")
    @field:PrimaryKey(autoGenerate = true)
    val fid: Int?,

    @field:ColumnInfo(name = "id")
    val id: String,

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String
)