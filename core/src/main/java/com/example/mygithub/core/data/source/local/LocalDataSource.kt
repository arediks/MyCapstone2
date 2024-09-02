package com.example.mygithub.core.data.source.local

import com.example.mygithub.core.data.source.local.datastore.SettingPreferences
import com.example.mygithub.core.data.source.local.entity.FavoriteEntity
import com.example.mygithub.core.data.source.local.entity.RemoteKeys
import com.example.mygithub.core.data.source.local.entity.UserListEntity
import com.example.mygithub.core.data.source.local.room.FavoriteDao
import com.example.mygithub.core.data.source.local.room.RemoteKeysDao
import com.example.mygithub.core.data.source.local.room.UserListDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val userListDao: UserListDao,
    private val favoriteDao: FavoriteDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val pref: SettingPreferences
) {

    // Favorite DAO
    fun getUserFavorite()= favoriteDao.getFavorite()

    suspend fun isFavorite(username: String) = favoriteDao.isFavorite(username)

    suspend fun insertFavorite(userFavorite: FavoriteEntity) = favoriteDao.insertFavorite(userFavorite)

    suspend fun deleteFavorite(userFavorite: FavoriteEntity) = favoriteDao.deleteFavorite(userFavorite)

    // Setting Preferences
    fun getThemeSetting() = pref.getThemeSetting()

    suspend fun saveThemeSetting(isDarkMode: Boolean) = pref.saveThemeSetting(isDarkMode)

    // User List Dao
    fun getUserList() = userListDao.getUserList()

    suspend fun insertUser(user: List<UserListEntity>) = userListDao.insertUser(user)

    suspend fun deleteUser() = userListDao.deleteUser()

    suspend fun updateUser(user: UserListEntity) = userListDao.updateUser(user)

    // Remote Keys Dao
    suspend fun insertAllRemoteKeys(remoteKeys: List<RemoteKeys>) = remoteKeysDao.insertAll(remoteKeys)

    suspend fun getRemoteKeysId(id: String) = remoteKeysDao.getRemoteKeysId(id)

    suspend fun deleteRemoteKeys() = remoteKeysDao.deleteRemoteKeys()

    suspend fun getSince(id: String) = remoteKeysDao.getSince(id)

}