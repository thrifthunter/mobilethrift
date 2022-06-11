package com.thrifthunter.activity.categorySweatShirt

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.thrifthunter.auth.LoginActivity
import com.thrifthunter.databinding.ActivitySweatShirtCategoryBinding
import com.thrifthunter.paging.LoadingStateAdapter
import com.thrifthunter.tools.*
import com.thrifthunter.tools.response.ListItemBarang
import com.thrifthunter.tools.response.ValuesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SweatShirtCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySweatShirtCategoryBinding
    private lateinit var sweatShirtCategoryViewModel: SweatShirtCategoryViewModel

    private var index : Int = 1
    private var maxIndex : Int = 1
    private var arrayList = ArrayList<ValuesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySweatShirtCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setViewModel()
        index = 1;
        getAllData()

        binding.refresh.setOnClickListener { getAllData() }
        binding.imgNext.setOnClickListener { nextIndex() }
        binding.imgPrev.setOnClickListener { prevIndex() }
        binding.tvIndex.text = index.toString()
    }

    private fun prevIndex() {
        Log.i("current index", index.toString())
        if (index > 1) {
            index = index - 1
            binding.tvIndex.text = index.toString()
            getIndexBarang(index, "")
        }
        Log.i("now index", index.toString())

    }

    private fun nextIndex() {
        Log.i("current index", index.toString())
        if (index < maxIndex) {
            index = index + 1
            binding.tvIndex.text = index.toString()
            getIndexBarang(index, "")
        }
        Log.i("now index", index.toString())
    }

    private fun getAllData() {
        binding.progressBar.setVisibility(View.VISIBLE)
        val service = ApiConfig().getApiService().getProductItem("",0, 0, "sweatshirt")
        service.enqueue(object : Callback<ListItemBarang> {
            override fun onResponse(
                call: Call<ListItemBarang>,
                response: Response<ListItemBarang>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        binding.progressBar.setVisibility(View.GONE)
                        Log.i("data ini", responseBody.toString())
                        maxIndex = Math.ceil((responseBody.values?.size!!.toDouble() / 5)).roundToInt()
                        getIndexBarang(1, "")
                        index = 1
                        binding.tvIndex.text = index.toString()
                    }
                } else {
                    Log.i("data ini", "error 1")
                    binding.progressBar.setVisibility(View.GONE)
                }
            }

            override fun onFailure(call: Call<ListItemBarang>, t: Throwable) {
                Log.i("data ini", "error 2")
                binding.progressBar.setVisibility(View.GONE)
            }
        })
    }

    private fun getIndexBarang(indeksPage:Int, name : String) {
        binding.progressBar.setVisibility(View.VISIBLE)
        val service = ApiConfig().getApiService().getProductItem(name,indeksPage, 5, "sweatshirt")
        service.enqueue(object : Callback<ListItemBarang> {
            override fun onResponse(
                call: Call<ListItemBarang>,
                response: Response<ListItemBarang>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        binding.progressBar.setVisibility(View.GONE)
                        Log.i("data ini", responseBody.toString())
                        arrayList = ArrayList<ValuesItem>()
                        arrayList = responseBody.values as ArrayList<ValuesItem>
                        Log.i("dataaaa woe", arrayList.toString())
                        Log.i("jumlah data", arrayList.size.toString())
                        showRecyclerList()
                    }
                } else {
                    binding.progressBar.setVisibility(View.GONE)
                    Log.i("data ini", "error 1")
                }
            }

            override fun onFailure(call: Call<ListItemBarang>, t: Throwable) {
                binding.progressBar.setVisibility(View.GONE)
                Log.i("data ini", "error 2")
            }
        })
    }

    private fun showRecyclerList() {
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        val listItemAdapter = ListBarangAdapter(arrayList)
        binding.recycleView.adapter = listItemAdapter
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
        supportActionBar?.title = "SweatShirt"
    }

    private fun setViewModel() {

        UserPreference.getInstance(dataStore).getItems().asLiveData().observe(this) { userData ->
            val getToken = userData.token

            sweatShirtCategoryViewModel = ViewModelProvider(
                this,
                ViewModelFactory(UserPreference.getInstance(dataStore), getToken)
            )[SweatShirtCategoryViewModel::class.java]

            sweatShirtCategoryViewModel.getStories().observe(this) { user ->
                if (!user.status) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }

            binding.recycleView.layoutManager = LinearLayoutManager(this)
            val adapter = ListUserAdapter()
            binding.recycleView.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter{
                    adapter.retry()
                }
            )

            sweatShirtCategoryViewModel.item.observe(this) {
                if (it!=null) {
                    adapter.submitData(lifecycle, it)
                    binding.recycleView.adapter = adapter
                } else {
                }
            }
        }
    }
}