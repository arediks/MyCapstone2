package com.example.mygithub.ui.detailUserActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.core.domain.usecase.GitUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val gitUserUseCase: GitUserUseCase) : ViewModel() {
    fun getUserDetail(username: String) = gitUserUseCase.getUserDetail(username).asLiveData()

    fun insertFavorite(userFavorite: GitUserList) {
        viewModelScope.launch {
            gitUserUseCase.insertFavorite(userFavorite)
        }
    }

    fun deleteFavorite(userFavorite: GitUserList) {
        viewModelScope.launch {
            gitUserUseCase.deleteFavorite(userFavorite)
        }
    }
}