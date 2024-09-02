package com.example.mygithub.ui.mainActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.R
import com.example.mygithub.adapter.MainSuggestionAdapter
import com.example.mygithub.adapter.MainUserAdapter
import com.example.mygithub.core.data.source.paging3.LoadingStateAdapter
import com.example.mygithub.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import reactivecircus.flowbinding.android.widget.textChanges

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainUserAdapter: MainUserAdapter
    private var isDarkMode = false
    private var adapterPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        Thread.sleep(1000L)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getThemeSettings().observe(this) {
            isDarkMode = if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.btnUiMode.icon = ContextCompat.getDrawable(this, R.drawable.baseline_emergency_24)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.btnUiMode.icon = ContextCompat.getDrawable(this, R.drawable.baseline_brightness_2_24)
                false
            }
        }

        mainUserAdapter = MainUserAdapter { favorite, position ->
            if (favorite.isFavorite) {
                favorite.isFavorite = false
                mainViewModel.deleteFavorite(favorite)
                adapterPosition = position
            } else {
                favorite.isFavorite = true
                mainViewModel.insertFavorite(favorite)
                adapterPosition = position
            }
        }

        binding.rvUsers.apply {
            adapter = mainUserAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    mainUserAdapter.retry()
                }
            )
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        with(binding) {
            searchviewUser.setupWithSearchBar(searchbarUser)
            searchviewUser
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    if (searchviewUser.text.isNotBlank()) {
                        mainViewModel.getUserSearch(searchviewUser.text.toString())
                    } else {
                        mainViewModel.getUserList()
                    }
                    mainViewModel.setSearchQuery(searchviewUser.text.toString())
                    searchviewUser.clearFocus()
                    searchviewUser.hide()
                    false
                }
        }

        val suggestionAdapter = MainSuggestionAdapter { searchQuery ->
            binding.searchviewUser.setText(searchQuery)
            binding.searchviewUser.editText.setSelection(searchQuery.length)
        }
        binding.rvSearchView.apply {
            adapter = suggestionAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        // SearchViewBinding
        val searchViewBinding = binding.searchviewUser.editText
            .textChanges()
            .skipInitialValue()
            .mapLatest {
                it.toString()
            }
            .debounce(300)
            .distinctUntilChanged()
            .asLiveData().switchMap {
                if (it.isBlank()) {
                    flowOf(emptyList<String>()).asLiveData()
                } else {
                    mainViewModel.getSearchSuggestion(it)
                }
            }
        searchViewBinding.observe(this) {
            suggestionAdapter.submitList(it)
        }

        binding.btnMenuFavorite.setOnClickListener {
            val uri = Uri.parse("mygithub://favoritefeature")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        binding.btnUiMode.setOnClickListener {
            mainViewModel.saveThemeSetting(!isDarkMode)
        }

        mainViewModel.searchQuery.observe(this) {
            binding.searchbarUser.hint =
                it.ifBlank {
                    DEFAULT_SEARCH_QUERY
                }
            generateUser()
        }
    }

    private fun generateUserList() {
        mainViewModel.uiUserList.observe(this) {
            mainUserAdapter.submitData(lifecycle, it)
            if (adapterPosition != null) {
                mainUserAdapter.notifyItemChanged(adapterPosition!!)
                adapterPosition = null
            }
        }
        binding.userNoFound.visibility = View.GONE
    }

    private fun generateUserSearch() {
        mainViewModel.uiUserSearch.observe(this) {
            mainUserAdapter.submitData(lifecycle, it)
            if (adapterPosition != null) {
                mainUserAdapter.notifyItemChanged(adapterPosition!!)
                adapterPosition = null
            }
        }

        mainUserAdapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached) {
                binding.userNoFound.visibility =
                    if (mainUserAdapter.itemCount == 0) View.VISIBLE else View.GONE
            }
        }
    }

    private fun generateUser() {
        if (binding.searchbarUser.hint == DEFAULT_SEARCH_QUERY) {
            generateUserList()
        } else {
            generateUserSearch()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.searchbarUser.hint != DEFAULT_SEARCH_QUERY) {
            mainViewModel.getUserList()
            mainViewModel.setSearchQuery("")
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }

    companion object {
        const val DEFAULT_SEARCH_QUERY = "Cari Pengguna"
    }
}
