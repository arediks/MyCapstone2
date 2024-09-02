package com.example.mygithub.di

import com.example.mygithub.core.domain.usecase.GitUserInteractor
import com.example.mygithub.core.domain.usecase.GitUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideGitUserUseCase(gitUserInteractor: GitUserInteractor): GitUserUseCase
}