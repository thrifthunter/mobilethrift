package com.thrifthunter.activity.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.thrifthunter.R
import com.thrifthunter.databinding.ActivityDetailBinding
import com.thrifthunter.helper.FavoriteHelper
import com.thrifthunter.tools.FavoriteData
import com.thrifthunter.tools.ProductData
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAV = "extra_data"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }

    private var isFavorite = false
    private lateinit var gitHelper: FavoriteHelper
    private var favorites: FavoriteData? = null
    private lateinit var imageAvatar: String
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gitHelper = FavoriteHelper.getInstance(applicationContext)
        gitHelper.open()

        favorites = intent.getParcelableExtra(EXTRA_NOTE)
        if (favorites != null) {
            setDataObject()
            isFavorite = true
            val checked: Int = R.drawable.ic_favorite_favorite_filled
            btn_fav.setImageResource(checked)
        } else {
            setData()
        }

        setView()
        setData()
    }

    private fun setData() {
        val user = intent.getParcelableExtra<ProductData>("User") as ProductData
        Glide.with(applicationContext)
            .load(user.photoUrl)
            .into(binding.detailPhoto)
        binding.detailName.text = user.name
        binding.detailAkun.text = user.account
        binding.detailHarga.text = user.price
        binding.detailDescription.text = user.description
    }

    private fun setView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataObject() {
        val favoriteUser = intent.getParcelableExtra(EXTRA_NOTE) as FavoriteData?
        favoriteUser?.name?.let { setActionBarTitle(it) }
        detail_name.text = favoriteUser?.name
        detail_description.text = favoriteUser?.description
        detail_akun.text = favoriteUser?.account
        detail_harga.text = favoriteUser?.price
        Glide.with(this)
            .load(favoriteUser?.photoUrl)
            .into(detail_photo)
        imageAvatar = favoriteUser?.photoUrl.toString()
    }
}
