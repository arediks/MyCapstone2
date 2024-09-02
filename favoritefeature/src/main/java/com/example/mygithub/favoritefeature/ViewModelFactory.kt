package com.example.mygithub.favoritefeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygithub.core.domain.usecase.GitUserUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val gitUserUseCase: GitUserUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(UserFavoriteViewModel::class.java) -> {
                UserFavoriteViewModel(gitUserUseCase) as T
            }

            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
}