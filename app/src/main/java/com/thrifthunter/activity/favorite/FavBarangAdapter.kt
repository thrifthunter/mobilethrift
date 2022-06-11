package com.thrifthunter.activity.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thrifthunter.R
import com.thrifthunter.activity.detail.Database.Favorites
import com.thrifthunter.activity.detail.Database.FavoritesDB
import com.thrifthunter.activity.detail.DetailActivity
import com.thrifthunter.tools.response.ValuesItem


class FavBarangAdapter(private val listfav: ArrayList<Favorites>) : RecyclerView.Adapter<FavBarangAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listfav[position])
    }

    override fun getItemCount(): Int = listfav.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var photoPost: ImageView = itemView.findViewById(R.id.img_item_photo)
        private var namePost: TextView = itemView.findViewById(R.id.tv_item_name)
        private var descPost: TextView = itemView.findViewById(R.id.tv_item_description)

        fun bind(barangModel: Favorites) {
            Picasso.get().load("https://picsum.photos/500").into(photoPost);
//            Picasso.get().load(barangModel.photoUrl).into(photoPost);
            namePost.text = barangModel.name
            descPost.text = barangModel.description

            itemView.setOnClickListener {
                val valueItem = ValuesItem(barangModel.price,barangModel.photoUrl,barangModel.name,barangModel.description,barangModel.id.toString(),barangModel.category,barangModel.account)
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("DATA", valueItem)
                itemView.context.startActivity(intent)
            }
        }
    }

    fun setData(newList: List<Favorites>) {
        listfav.clear()
        listfav.addAll(newList)
    }
}