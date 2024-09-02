package com.example.mygithub.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mygithub.ui.userDetailFollowFragment.UserDetailFollowFragment

class FollowPagerAdapter(activity: AppCompatActivity, private val userFollow: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = UserDetailFollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(UserDetailFollowFragment.ARG_SECTION_NUMBER, position)
            putString(UserDetailFollowFragment.ARG_SECTION_USER, userFollow)
        }
        return fragment
    }
}