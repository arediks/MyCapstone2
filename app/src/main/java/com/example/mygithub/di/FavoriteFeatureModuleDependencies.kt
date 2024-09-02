package com.example.mygithub.di

import com.example.mygithub.core.domain.usecase.GitUserUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteFeatureModuleDependencies {

    fun gitUserUseCase(): GitUserUseCase
}