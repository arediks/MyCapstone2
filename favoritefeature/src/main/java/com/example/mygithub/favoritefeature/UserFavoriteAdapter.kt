package com.example.mygithub.favoritefeature

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.favoritefeature.databinding.RecyclerviewFavoriteUserBinding
import com.example.mygithub.ui.detailUserActivity.DetailUserActivity

class UserFavoriteAdapter(private val onFavoriteClick: (GitUserList) -> Unit) : ListAdapter<GitUserList, UserFavoriteAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(var binding: RecyclerviewFavoriteUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userFavorite: GitUserList) {
            Glide.with(itemView.context).load(userFavorite.avatarUrl).into(binding.ivUser)
            binding.tvUserName.text = userFavorite.login
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, GitUserList(userFavorite.id, userFavorite.login, userFavorite.avatarUrl, true))
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerviewFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userFavorite = getItem(position)
        holder.bind(userFavorite)

        holder.binding.btnUserFavorite.setOnClickListener {
            onFavoriteClick(userFavorite)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GitUserList>() {
            override fun areItemsTheSame(
                oldItem: GitUserList,
                newItem: GitUserList
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GitUserList,
                newItem: GitUserList
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}