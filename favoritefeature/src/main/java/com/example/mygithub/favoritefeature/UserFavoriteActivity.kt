package com.example.mygithub.favoritefeature

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.core.data.Result
import com.example.mygithub.di.FavoriteFeatureModuleDependencies
import com.example.mygithub.favoritefeature.databinding.ActivityUserFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class UserFavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val userFavoriteViewModel: UserFavoriteViewModel by viewModels {
        factory
    }

    private lateinit var binding: ActivityUserFavoriteBinding
    private lateinit var userFavoriteAdapter: UserFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerUserFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteFeatureModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        userFavoriteAdapter = UserFavoriteAdapter { favorite ->
            favorite.isFavorite = false
            userFavoriteViewModel.deleteFavorite(favorite)
        }

        binding.rvUsers.apply {
            adapter = userFavoriteAdapter
            layoutManager = LinearLayoutManager(this@UserFavoriteActivity)
            setHasFixedSize(true)
        }

        userFavoriteViewModel.getUserFavoriteList().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val userFavorite = result.data
                        userFavoriteAdapter.submitList(userFavorite)
                        binding.tvEmptyFavorite.visibility =
                            if (userFavorite.isEmpty()) View.VISIBLE else View.GONE
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Terjadi kesalahan: ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        const val ACTIVITY_TITLE = "Favorite User"
    }
}