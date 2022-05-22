package com.thrifthunter.settings

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
import com.thrifthunter.DetailActivity
import com.thrifthunter.Data
import com.thrifthunter.databinding.UserItemBinding

class ListUserAdapter: PagingDataAdapter<ListStory, ListUserAdapter.ListViewHolder>(DIFF_CALLBACK) {

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

        fun bind(user: ListStory) {
            Glide.with(binding.root.context)
                .load(user.photoUrl)
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = user.name

            binding.root.setOnClickListener {
                val dataUser = Data(user.name, user.photoUrl, user.description, user.akun, user.harga)
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("User", dataUser)

                val optionsCompat : ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        binding.root.context as Activity,
                        Pair(binding.tvItemName, "nameStory"),
                        Pair(binding.imgItemPhoto, "imageStory"),
                    )
                binding.root.context.startActivity(intent, optionsCompat.toBundle())
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStory>() {
            override fun areItemsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}