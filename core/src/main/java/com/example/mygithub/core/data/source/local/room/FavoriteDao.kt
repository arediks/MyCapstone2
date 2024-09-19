package com.example.mygithub.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mygithub.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("select * from favorite order by fid desc")
    fun getFavorite(): Flow<List<FavoriteEntity>>

    @Query("select exists (select login from favorite where login = :username)")
    suspend fun isFavorite(username: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("delete from favorite where id = :id")
    suspend fun deleteFavorite(id: String)
}