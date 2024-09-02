package com.example.mygithub.favoritefeature

import android.content.Context
import com.example.mygithub.di.FavoriteFeatureModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteFeatureModuleDependencies::class])
interface UserFavoriteComponent {

    fun inject(activity: UserFavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteFeatureModuleDependencies: FavoriteFeatureModuleDependencies): Builder
        fun build(): UserFavoriteComponent
    }
}