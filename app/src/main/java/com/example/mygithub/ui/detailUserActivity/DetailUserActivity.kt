package com.example.mygithub.ui.detailUserActivity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.adapter.FollowPagerAdapter
import com.example.mygithub.core.data.Result
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userExtra: GitUserList? = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER, GitUserList::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER)
        }

        if (userExtra == null) {
            Toast.makeText(this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show()
            finish()
        }
        val userData = userExtra ?: GitUserList("","","",false)
        generateDetailUser(userData.login)

        setFavoriteIcon(userData.isFavorite)

        binding.fabFavorite.setOnClickListener {
            @Suppress("KotlinConstantConditions")
            if (userData.isFavorite) {
                detailUserViewModel.deleteFavorite(userData)
                userData.isFavorite = false
                setFavoriteIcon(userData.isFavorite)

            } else {
                detailUserViewModel.insertFavorite(userData)
                userData.isFavorite = true
                setFavoriteIcon(userData.isFavorite)
            }
            generateDetailUser(userData.login)
        }

        val followPagerAdapter = FollowPagerAdapter(this, userData.login)
        val viewPager: ViewPager2 = binding.vpDetailFollow
        viewPager.adapter = followPagerAdapter
        val tabs: TabLayout = binding.tlDetailFollow
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun generateDetailUser(username: String) {
        detailUserViewModel.getUserDetail(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.detailuserProgresbar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.detailuserProgresbar.visibility = View.GONE
                        val userDetail = result.data
                        Glide.with(this).load(userDetail.avatarUrl).into(binding.ivDetailUser)
                        binding.apply {
                            tvDetailName.text = userDetail.name
                            tvDetailUsername.text = userDetail.login
                            if (userDetail.name.isBlank()) {
                                tvDetailName.visibility = View.GONE
                                tvDetailUsername.textSize = 40f
                                changeConstraint(tvDetailUsername)
                            }
                            tvDetailFollowerNumber.text = userDetail.followers.toString()
                            tvDetailFollowingNumber.text = userDetail.following.toString()
                        }
                    }

                    is Result.Error -> {
                        binding.detailuserProgresbar.visibility = View.GONE
                        Toast.makeText(this,
                            "Terjadi kesalahan: ${result.error}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        val fabFavorite = binding.fabFavorite
        if (isFavorite) {
            fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    fabFavorite.context,
                    R.drawable.baseline_favorite_24)
            )

        } else {
            fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    fabFavorite.context,
                    R.drawable.baseline_favorite_border_24
                )
            )
        }
    }

    private fun changeConstraint(textView: TextView) {
        val constraint = textView.layoutParams as ConstraintLayout.LayoutParams
        constraint.startToEnd = binding.ivDetailUser.id
        constraint.topToTop = binding.ivDetailUser.id
        constraint.bottomToBottom = binding.ivDetailUser.id
        val margin = textView.layoutParams as ViewGroup.MarginLayoutParams
        margin.marginStart = 40
        textView.layoutParams = margin
        binding.tvDetailUsername.requestLayout()
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        private val TAB_TITLES = intArrayOf(
            R.string.detail_followers,
            R.string.detail_following
        )
    }
}