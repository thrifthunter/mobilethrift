package com.thrifthunter.tools

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thrifthunter.activity.detail.DetailActivity
import com.thrifthunter.databinding.UserItemBinding
import com.thrifthunter.tools.ListItem
import com.thrifthunter.tools.ProductData

class ListUserAdapter: PagingDataAdapter<ListItem, ListUserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if( data != null) {
            holder.bind(data)
        }
    }


    class ListViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ListItem) {
            Glide.with(binding.root.context)
                .load(user.photoUrl)
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = user.name
            binding.tvItemDescription.text = user.account

            binding.root.setOnClickListener {
                val dataUser = ProductData(user.name, user.photoUrl, user.description, user.account, user.price, user.category)
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("User", dataUser)

                val optionsCompat : ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        binding.root.context as Activity,
                        Pair(binding.tvItemName, "name"),
                        Pair(binding.imgItemPhoto, "image"),
                        Pair(binding.tvItemDescription, "account")
                    )
                binding.root.context.startActivity(intent, optionsCompat.toBundle())
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}