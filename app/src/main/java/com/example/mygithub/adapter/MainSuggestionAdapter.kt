package com.example.mygithub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mygithub.databinding.RecyclerviewSearchviewSuggestionBinding

class MainSuggestionAdapter(private val searchQuery: (String) -> Unit) : ListAdapter<String, MainSuggestionAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(private var binding: RecyclerviewSearchviewSuggestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(suggestion: String) {
            binding.tvUserName.text = suggestion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerviewSearchviewSuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val searchSuggestion = getItem(position)
        holder.bind(searchSuggestion)

        holder.itemView.setOnClickListener {
            searchQuery(searchSuggestion)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}