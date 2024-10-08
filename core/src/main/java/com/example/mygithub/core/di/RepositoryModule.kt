package com.example.mygithub.core.di

import com.example.mygithub.core.data.Repository
import com.example.mygithub.core.domain.repository.IGitUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(gitUserRepository: Repository): IGitUserRepository
}