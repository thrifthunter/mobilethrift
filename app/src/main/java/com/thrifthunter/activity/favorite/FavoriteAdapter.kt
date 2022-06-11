package com.thrifthunter.activity.favorite

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thrifthunter.CustomOnItemClickListener
import com.thrifthunter.R
import com.thrifthunter.activity.detail.DetailActivity
import com.thrifthunter.tools.FavoriteData
import kotlinx.android.synthetic.main.user_item.view.*

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.NoteViewHolder>() {
    var listFavorite = ArrayList<FavoriteData>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: FavoriteData) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(fav.photoUrl)
                    .apply(RequestOptions().override(250, 250))
                    .into(itemView.img_item_photo)
                tv_item_name.text = fav.name
                tv_item_description.text = fav.description.toString()
                itemView.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, DetailActivity::class.java)
//                                intent.putExtra(DetailActivity.EXTRA_POSITION, position)
//                                intent.putExtra(DetailActivity.EXTRA_NOTE, fav)
                                activity.startActivity(intent)
                            }
                        }
                    )
                )
            }
        }
    }
}