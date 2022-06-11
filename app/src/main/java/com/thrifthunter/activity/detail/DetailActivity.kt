package com.thrifthunter.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.thrifthunter.R
import com.thrifthunter.activity.detail.Database.Favorites
import com.thrifthunter.activity.detail.Database.FavoritesDB
import com.thrifthunter.databinding.ActivityDetailBinding
import com.thrifthunter.tools.response.ValuesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

//    companion object {
//        const val EXTRA_DATA = "extra_data"
//        const val EXTRA_FAV = "extra_data"
//        const val EXTRA_NOTE = "extra_note"
//        const val EXTRA_POSITION = "extra_position"
//    }
//
//    private var isFavorite = false
//    private lateinit var gitHelper: FavoriteHelper
//    private var favorites: FavoriteData? = null
//    private lateinit var imageAvatar: String
    private lateinit var binding: ActivityDetailBinding
    private var available : Boolean = false
    private val db by lazy { FavoritesDB(this) }
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        gitHelper = FavoriteHelper.getInstance(applicationContext)
//        gitHelper.open()
//
//        favorites = intent.getParcelableExtra(EXTRA_NOTE)
//        if (favorites != null) {
//            setDataObject()
//            isFavorite = true
//            val checked: Int = R.drawable.ic_favorite_favorite_filled
//            btn_fav.setImageResource(checked)
//        } else {
//            setData()
//        }
//
//        setView()
        setData()
        setupActionBar()
        val data = intent.getParcelableExtra<ValuesItem>("DATA")
        CoroutineScope(Dispatchers.IO).launch {
            var listUser = db.favoritesDao().findUserWithName(data?.name)
            if (listUser.size > 0) {
                available = true
                binding.btnfav.setImageResource(R.drawable.ic_favorite_favorite_filled)
            }
            else {
                available = false
                binding.btnfav.setImageResource(R.drawable.ic_favorite_favorite_unfilled)
            }
        }
        FavoritesButton()
    }
//
    private fun setData() {
        // if u want to fetch twt
//        Picasso.get().load(data?.photoUrl).into(binding.detailPhoto);

        // dummy image
        val data = intent.getParcelableExtra<ValuesItem>("DATA")
        Picasso.get().load("https://picsum.photos/500").into(binding.detailPhoto);
        binding.detailName.text = data?.name
        binding.detailAkun.text = data?.account
        binding.detailHarga.text = "Rp " + data?.price.toString()
        binding.detailDescription.text = data?.description
    }

    private fun FavoritesButton() {
        val data = intent.getParcelableExtra<ValuesItem>("DATA")
        binding.btnfav.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (available == true) {
//                    data?.name?.let { it1 -> db.favoritesDao().deleteByUsername(it1) }
                    db.favoritesDao().deleteByUsername(data?.name)
                    binding.btnfav.setImageResource(R.drawable.ic_favorite_favorite_unfilled)
                    available = false
                }
                else {
                    db.favoritesDao().addFavorites(
                        Favorites(
                            0,
                            data?.price,
                            data?.photoUrl,
                            data?.name,
                            data?.description,
                            data?.category,
                            data?.account,
                        )
                    )
                    binding.btnfav.setImageResource(R.drawable.ic_favorite_favorite_filled)
                    available = true
                }
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Produk"
    }
//
//    private fun setView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//    }
//
//    private fun setActionBarTitle(title: String) {
//        if (supportActionBar != null) {
//            this.title = title
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun setDataObject() {
//        val favoriteUser = intent.getParcelableExtra(EXTRA_NOTE) as FavoriteData?
//        favoriteUser?.name?.let { setActionBarTitle(it) }
//        detail_name.text = favoriteUser?.name
//        detail_description.text = favoriteUser?.description
//        detail_akun.text = favoriteUser?.account
//        detail_harga.text = favoriteUser?.price
//        Glide.with(this)
//            .load(favoriteUser?.photoUrl)
//            .into(detail_photo)
//        imageAvatar = favoriteUser?.photoUrl.toString()
//    }
}
