package com.example.mygithub.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mygithub.core.data.source.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("select * from remote_keys where id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeys?

    @Query("select id from remote_keys where nextKey = :id")
    suspend fun getSince(id: String): List<String>

    @Query("delete from remote_keys")
    suspend fun deleteRemoteKeys()
}