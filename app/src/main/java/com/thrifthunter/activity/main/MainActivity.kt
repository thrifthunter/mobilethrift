package com.thrifthunter.activity.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.thrifthunter.ApiConfig
import com.thrifthunter.activity.profile.ProfileActivity
import com.thrifthunter.R
import com.thrifthunter.ViewModelFactory
import com.thrifthunter.activity.categoryJacket.JacketCategoryActivity
import com.thrifthunter.activity.categoryShoes.ShoesCategoryActivity
import com.thrifthunter.activity.categoryJeans.JeansCategoryActivity
import com.thrifthunter.activity.categoryTshirt.TShirtCategoryActivity
import com.thrifthunter.auth.LoginActivity
import com.thrifthunter.tools.UserPreference
import com.thrifthunter.databinding.ActivityMainBinding
import com.thrifthunter.activity.favorite.FavoriteActivity
import com.thrifthunter.paging.LoadingStateAdapter
import com.thrifthunter.settings.ListUserAdapter
import com.thrifthunter.tools.LoginResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setViewModel()
        searchUser()

        binding.button1.setOnClickListener { goToTShirt() }
        binding.button2.setOnClickListener { goToJeans() }
        binding.button3.setOnClickListener { goToJacket() }
        binding.button4.setOnClickListener { goToShoes() }
    }

    private fun searchUser() { search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            if (query.isEmpty()) {
                return true
            } else {
//                listData.clear()
                getUserSearch(query)
            }
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }
    })
    }

    private fun getUserSearch(item: String) {
//        ini tempat buat make api nya

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
        supportActionBar?.title = "Story App"
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
//        val searchView = menu.findItem(binding.search).actionView as SearchView
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = resources.getString(R.string.search_hint)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            /*
//            ini klo search selesai ato OK
//             */
//            override fun onQueryTextSubmit(query: String): Boolean {
//                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
//                searchView.clearFocus()
//                return true
//            }
//
//            /*
//            ini buat respon tiap perubahan huruf
//             */
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
//        return true

        return true
    }

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

    private fun goToTShirt() {
        val i = Intent(this, TShirtCategoryActivity::class.java)
        startActivity(i)
    }

    private fun goToJeans() {
        val i = Intent(this, JeansCategoryActivity::class.java)
        startActivity(i)
    }

    private fun goToJacket() {
        val i = Intent(this, JacketCategoryActivity::class.java)
        startActivity(i)
    }

    private fun goToShoes() {
        val i = Intent(this, ShoesCategoryActivity::class.java)
        startActivity(i)
    }
}