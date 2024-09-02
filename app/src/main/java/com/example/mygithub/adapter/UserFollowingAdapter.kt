package com.example.mygithub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithub.core.domain.model.GitUsers
import com.example.mygithub.databinding.RecyclerviewDetailUserFollowBinding

class UserFollowingAdapter : ListAdapter<GitUsers, UserFollowingAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(private var binding: RecyclerviewDetailUserFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(following: GitUsers) {
            Glide.with(itemView.context).load(following.avatarUrl).into(binding.ivDetailUserFollow)
            binding.tvUserName.text = following.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerviewDetailUserFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userFollow = getItem(position)
        holder.bind(userFollow)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GitUsers>() {
            override fun areItemsTheSame(oldItem: GitUsers, newItem: GitUsers): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GitUsers,
                newItem: GitUsers
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}