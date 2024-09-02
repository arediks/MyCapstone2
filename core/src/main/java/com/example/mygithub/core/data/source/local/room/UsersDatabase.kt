package com.example.mygithub.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mygithub.core.data.source.local.entity.FavoriteEntity
import com.example.mygithub.core.data.source.local.entity.RemoteKeys
import com.example.mygithub.core.data.source.local.entity.UserListEntity

@Database(entities = [UserListEntity::class, FavoriteEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userListDao(): UserListDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}