package com.thrifthunter.activity.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.thrifthunter.R
import com.thrifthunter.tools.ViewModelFactory
import com.thrifthunter.activity.categoryHoodie.HoodieCategoryActivity
import com.thrifthunter.activity.categoryLongSleeve.LongSleeveCategoryActivity
import com.thrifthunter.activity.categoryShirt.ShirtCategoryActivity
import com.thrifthunter.activity.categorySweatShirt.SweatShirtCategoryActivity
import com.thrifthunter.activity.favorite.FavoriteActivity
import com.thrifthunter.activity.profile.ProfileActivity
import com.thrifthunter.auth.LoginActivity
import com.thrifthunter.databinding.ActivityMainBinding
import com.thrifthunter.paging.LoadingStateAdapter
import com.thrifthunter.tools.ListUserAdapter
import com.thrifthunter.tools.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
//    private var listItems: ArrayList<ProductData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setViewModel()
//        searchUser()

        binding.button1.setOnClickListener { goToHoodie() }
        binding.button2.setOnClickListener { goToLongSleeve() }
        binding.button3.setOnClickListener { goToShirt() }
        binding.button4.setOnClickListener { goToSweatShirt() }
        binding.refresh.setOnClickListener { refresh() }
    }

//    private fun searchUser() { search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//        override fun onQueryTextSubmit(query: String): Boolean {
//            if (query.isEmpty()) {
//                return true
//            } else {
//                listItems.clear()
//                findData(query)
//            }
//            return true
//        }
//
//        override fun onQueryTextChange(newText: String): Boolean {
//            listItems.clear()
//            binding.recycleView.adapter = ListUserAdapter(listItems)
//            return false
//        }
//    })
//    }

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