package com.example.mygithub.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.core.domain.usecase.GitUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val gitUserUseCase: GitUserUseCase) : ViewModel() {
    private val _searchQuery: MutableLiveData<String> = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    var uiUserList: LiveData<PagingData<GitUserList>> = MutableLiveData()
    var uiUserSearch: LiveData<PagingData<GitUserList>> = MutableLiveData()

    init {
        getUserList()
    }

    fun getUserList() {
        uiUserList = gitUserUseCase.getUserList().cachedIn(viewModelScope).asLiveData()
    }

    fun getUserSearch(username: String) {
        uiUserSearch = gitUserUseCase.getUserSearch(username).cachedIn(viewModelScope).asLiveData()
    }

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

    fun getThemeSettings() = gitUserUseCase.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            gitUserUseCase.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getSearchSuggestion(query: String): LiveData<List<String>> =
        gitUserUseCase.getSearchSuggestion(query).asLiveData()

    fun setSearchQuery(query: String) {
        if (query.isNotBlank()) {
            _searchQuery.value = query
        } else {
            _searchQuery.value = ""
        }
    }
}