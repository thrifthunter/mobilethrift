package com.thrifthunter.activity.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.thrifthunter.R
import com.thrifthunter.activity.categoryHoodie.HoodieCategoryActivity
import com.thrifthunter.activity.categoryLongSleeve.LongSleeveCategoryActivity
import com.thrifthunter.activity.categoryShirt.ShirtCategoryActivity
import com.thrifthunter.activity.categorySweatShirt.SweatShirtCategoryActivity
import com.thrifthunter.activity.favorite.FavoriteActivity
import com.thrifthunter.activity.profile.ProfileActivity
import com.thrifthunter.auth.LoginActivity
import com.thrifthunter.databinding.ActivityMainBinding
import com.thrifthunter.paging.LoadingStateAdapter
import com.thrifthunter.tools.*
import com.thrifthunter.tools.response.ListItemBarang
import com.thrifthunter.tools.response.ValuesItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var index : Int = 1
    private var maxIndex : Int = 1
    private var arrayList = ArrayList<ValuesItem>()
//    private var listItems: ArrayList<ProductData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setViewModel()
        index = 1;
        searchBarang()
        getAllData()

        binding.button1.setOnClickListener { goToHoodie() }
        binding.button2.setOnClickListener { goToLongSleeve() }
        binding.button3.setOnClickListener { goToShirt() }
        binding.button4.setOnClickListener { goToSweatShirt() }
        binding.refresh.setOnClickListener { getAllData() }
        binding.imgNext.setOnClickListener { nextIndex()}
        binding.imgPrev.setOnClickListener { prevIndex()}
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
        val service = ApiConfig().getApiService().getProductItem("",0, 0)
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
        val service = ApiConfig().getApiService().getProductItem(name,indeksPage, 5)
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

    private fun searchBarang() {
        val searchView : SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getIndexBarang(1, query)
                binding.tvIndex.text = "1"
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getIndexBarang(1, newText)
                binding.tvIndex.text = "1"
                return false
            }
        })
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
        supportActionBar?.title = "Thrift Hunter"
    }

    private fun setViewModel() {

        UserPreference.getInstance(dataStore).getItems().asLiveData().observe(this) { userData ->
            val getToken = userData.token

            mainViewModel = ViewModelProvider(
                this,
                ViewModelFactory(UserPreference.getInstance(dataStore), getToken)
            )[MainViewModel::class.java]

            mainViewModel.getStories().observe(this) { user ->
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

            mainViewModel.item.observe(this) {
                if (it!=null) {
                    adapter.submitData(lifecycle, it)
                    binding.recycleView.adapter = adapter
                } else {
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu?.findItem(R.id.search)?.actionView as androidx.appcompat.widget.SearchView
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                findData(query)
//                searchView.setQuery("", false)
//                searchView.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                listItems.clear()
//                binding.recycleView.adapter = ListUserAdapter(listItems)
//                return false
//            }
//
//        })
        return true
    }

//    private fun findData(query: String) {
//        val client = ApiConfig().getApiService().getStories(query,)
//        client.enqueue(object : Callback<GetResponse> {
//            override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setData(responseBody.listItem)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<GetResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: False")
//                Toast.makeText(this@MainActivity, "Gagal mengambil data", Toast.LENGTH_LONG).show()
//            }
//
//        })
//    }

    @Suppress("UNUSED_EXPRESSION", "UNREACHABLE_CODE")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                mainViewModel.logout()
                true
            }
            R.id.profile -> {
                val mIntent = Intent(this, ProfileActivity::class.java)
                startActivity(mIntent)
            }
            R.id.favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToHoodie() {
        val i = Intent(this, HoodieCategoryActivity::class.java)
        startActivity(i)
    }

    private fun goToShirt() {
        val i = Intent(this, ShirtCategoryActivity::class.java)
        startActivity(i)
    }

    private fun goToLongSleeve() {
        val i = Intent(this, LongSleeveCategoryActivity::class.java)
        startActivity(i)
    }

    private fun goToSweatShirt() {
        val i = Intent(this, SweatShirtCategoryActivity::class.java)
        startActivity(i)
    }

    private fun refresh() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}