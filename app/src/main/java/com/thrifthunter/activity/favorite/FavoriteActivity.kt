package com.thrifthunter.activity.favorite

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thrifthunter.R
import com.thrifthunter.activity.detail.Database.Favorites
import com.thrifthunter.activity.detail.Database.FavoritesDB
import com.thrifthunter.database.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.thrifthunter.databinding.ActivityDetailBinding
import com.thrifthunter.databinding.ActivityFavoriteBinding
import com.thrifthunter.helper.MappingHelper
import com.thrifthunter.tools.FavoriteData
import com.thrifthunter.tools.response.ValuesItem
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.*


class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val db by lazy { FavoritesDB(this) }
    lateinit var favBarangAdapter: FavBarangAdapter
    private var flag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        binding.tvNull.setVisibility(View.VISIBLE)
        binding.progressBar.setVisibility(View.VISIBLE)
        setupRecyclerView()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Wish List"
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            var favBarang = db.favoritesDao().getFavorites()
            if (favBarang.size == 0) {
                flag = true
            }
            else {
                flag = false
            }
            favBarangAdapter.setData(favBarang)
            withContext(Dispatchers.Main) {
                favBarangAdapter.notifyDataSetChanged()
            }
        }
        if (flag) {
            binding.tvNull.setVisibility(View.VISIBLE)
            binding.progressBar.setVisibility(View.GONE)
        }
    }
    private fun setupRecyclerView () {
        if (flag) {
            binding.tvNull.setVisibility(View.VISIBLE)
            binding.progressBar.setVisibility(View.GONE)
        }
        else {
            favBarangAdapter = FavBarangAdapter(arrayListOf())
            binding.recycleView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = favBarangAdapter
            }
            binding.tvNull.setVisibility(View.GONE)
            binding.progressBar.setVisibility(View.GONE)
        }
    }
}
