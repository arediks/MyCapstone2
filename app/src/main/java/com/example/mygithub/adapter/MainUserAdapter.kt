package com.example.mygithub.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.core.domain.model.GitUserList
import com.example.mygithub.databinding.RecyclerviewMainUserBinding
import com.example.mygithub.ui.detailUserActivity.DetailUserActivity

class MainUserAdapter(private val onFavoriteClick: (GitUserList, Int) -> Unit) : PagingDataAdapter<GitUserList, MainUserAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    class MyViewHolder(var binding: RecyclerviewMainUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GitUserList) {
            Glide.with(itemView.context).load(user.avatarUrl).into(binding.ivUser)
            binding.tvUserName.text = user.login
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, GitUserList(user.id, user.login, user.avatarUrl, user.isFavorite))
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerviewMainUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userFollow = getItem(position)
        if (userFollow != null) {
            holder.bind(userFollow)

            val btnUserFavorite = holder.binding.btnUserFavorite
            if (userFollow.isFavorite) {
                btnUserFavorite.icon = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.baseline_favorite_24
                )
            } else {
                btnUserFavorite.icon = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.baseline_favorite_border_24
                )
            }

            holder.binding.btnUserFavorite.setOnClickListener {
                onFavoriteClick(userFollow, position)
            }
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
