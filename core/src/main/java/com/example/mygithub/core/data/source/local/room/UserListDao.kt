package com.example.mygithub.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mygithub.core.data.source.local.entity.UserListEntity

@Dao
interface UserListDao {
    @Query("select * from users")
    fun getUserList(): PagingSource<Int, UserListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<UserListEntity>)

    @Query("delete from users")
    suspend fun deleteUser()

    @Update
    suspend fun updateUser(user: UserListEntity)
}