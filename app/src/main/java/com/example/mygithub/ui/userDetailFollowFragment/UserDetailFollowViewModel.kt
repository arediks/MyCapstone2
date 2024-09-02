package com.example.mygithub.ui.userDetailFollowFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mygithub.core.domain.usecase.GitUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailFollowViewModel @Inject constructor(private val gitUserUseCase: GitUserUseCase) : ViewModel() {
    fun getUserFollowers(username: String) = gitUserUseCase.getUserFollowers(username).asLiveData()

    fun getUserFollowing(username: String) = gitUserUseCase.getUserFollowing(username).asLiveData()
}