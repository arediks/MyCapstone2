package com.example.mygithub.core.di

import android.content.Context
import androidx.room.Room
import com.example.mygithub.core.data.source.local.datastore.SettingPreferences
import com.example.mygithub.core.data.source.local.datastore.datastore
import com.example.mygithub.core.data.source.local.room.FavoriteDao
import com.example.mygithub.core.data.source.local.room.RemoteKeysDao
import com.example.mygithub.core.data.source.local.room.UserListDao
import com.example.mygithub.core.data.source.local.room.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UsersDatabase {
        val passphrase = SQLiteDatabase.getBytes("capstone2".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            UsersDatabase::class.java, "Capstone2.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideFavoriteDao(database: UsersDatabase): FavoriteDao = database.favoriteDao()

    @Provides
    fun provideUserListDao(database: UsersDatabase): UserListDao = database.userListDao()

    @Provides
    fun provideRemoteKeysDao(database: UsersDatabase): RemoteKeysDao = database.remoteKeysDao()

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): SettingPreferences = SettingPreferences(context.datastore)
}