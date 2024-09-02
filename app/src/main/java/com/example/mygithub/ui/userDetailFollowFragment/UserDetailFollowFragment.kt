package com.example.mygithub.ui.userDetailFollowFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.R
import com.example.mygithub.adapter.UserFollowersAdapter
import com.example.mygithub.adapter.UserFollowingAdapter
import com.example.mygithub.core.data.Result
import com.example.mygithub.databinding.FragmentUserDetailFollowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFollowFragment : Fragment() {
    private var _binding: FragmentUserDetailFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersAdapter: UserFollowersAdapter
    private lateinit var followingAdapter: UserFollowingAdapter
    private val userFollowViewModel: UserDetailFollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_SECTION_USER) ?: ""
        followersAdapter = UserFollowersAdapter()
        followingAdapter = UserFollowingAdapter()

        binding.rvDetailFollow.layoutManager = LinearLayoutManager(view.context)

        if (index == 0) {
            generateUserFollowers(username)
        } else {
            generateUserFollowing(username)
        }
    }

    private fun generateUserFollowers(username: String) {
        userFollowViewModel.getUserFollowers(username).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.detailProgressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.detailProgressBar.visibility = View.GONE
                        val userFollowers = result.data
                        followersAdapter.submitList(userFollowers)
                        binding.rvDetailFollow.adapter = followersAdapter
                        if (userFollowers.isEmpty()) {
                            with(binding.tvNoFollow) {
                                visibility = View.VISIBLE
                                text = getString(R.string.no_followers)
                            }
                        } else {
                            binding.tvNoFollow.visibility = View.GONE
                        }
                    }

                    is Result.Error -> {
                        binding.detailProgressBar.visibility = View.GONE
                        Toast.makeText(view?.context, "Terjadi kesalahan: ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun generateUserFollowing(username: String) {
        userFollowViewModel.getUserFollowing(username).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.detailProgressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.detailProgressBar.visibility = View.GONE
                        val userFollowing = result.data
                        followingAdapter.submitList(userFollowing)
                        binding.rvDetailFollow.adapter = followingAdapter
                        if (userFollowing.isEmpty()) {
                            with(binding.tvNoFollow) {
                                visibility = View.VISIBLE
                                text = getString(R.string.no_following)
                            }
                        } else {
                            binding.tvNoFollow.visibility = View.GONE
                        }
                    }

                    is Result.Error -> {
                        binding.detailProgressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_SECTION_USER = "section_user"
    }
}