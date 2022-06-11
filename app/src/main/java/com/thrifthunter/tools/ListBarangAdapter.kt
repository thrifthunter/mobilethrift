package com.thrifthunter.tools

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thrifthunter.R
import com.thrifthunter.activity.detail.DetailActivity
import com.thrifthunter.tools.response.ValuesItem

class ListBarangAdapter(private val listBarang: ArrayList<ValuesItem>) : RecyclerView.Adapter<ListBarangAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBarang[position])
    }

    override fun getItemCount(): Int = listBarang.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var photoPost: ImageView = itemView.findViewById(R.id.img_item_photo)
        private var namePost: TextView = itemView.findViewById(R.id.tv_item_name)
        private var descPost: TextView = itemView.findViewById(R.id.tv_item_description)

        fun bind(barangModel: ValuesItem) {
            Picasso.get().load("https://picsum.photos/500").into(photoPost);
//            Picasso.get().load(barangModel.photoUrl).into(photoPost);
            namePost.text = barangModel.name
            descPost.text = barangModel.description

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("DATA", barangModel)
                itemView.context.startActivity(intent)
            }
        }
    }
}