package com.example.mygithub.favoritefeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.core.domain.usecase.GitUserUseCase
import kotlinx.coroutines.launch

class UserFavoriteViewModel(private val gitUserUseCase: GitUserUseCase) : ViewModel() {
    fun getUserFavoriteList() = gitUserUseCase.getUserFavorite().asLiveData()

    fun deleteFavorite(favorite: GitUserList) {
        viewModelScope.launch {
            gitUserUseCase.deleteFavorite(favorite)
        }
    }
}