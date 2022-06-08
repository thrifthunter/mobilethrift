package com.thrifthunter.activity.categoryJacket

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.thrifthunter.ViewModelFactory
import com.thrifthunter.auth.LoginActivity
import com.thrifthunter.databinding.ActivityJacketCategoryBinding
import com.thrifthunter.paging.LoadingStateAdapter
import com.thrifthunter.settings.ListUserAdapter
import com.thrifthunter.tools.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class JacketCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJacketCategoryBinding
    private lateinit var jacketCategoryViewModel: JacketCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJacketCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setViewModel()
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
        supportActionBar?.title = "Jacket"
    }

    private fun setViewModel() {

        UserPreference.getInstance(dataStore).getItems().asLiveData().observe(this) { userData ->
            val getToken = userData.token

            jacketCategoryViewModel = ViewModelProvider(
                this,
                ViewModelFactory(UserPreference.getInstance(dataStore), getToken)
            )[JacketCategoryViewModel::class.java]

            jacketCategoryViewModel.getStories().observe(this) { user ->
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

            jacketCategoryViewModel.item.observe(this) {
                if (it!=null) {
                    adapter.submitData(lifecycle, it)
                    binding.recycleView.adapter = adapter
                } else {
                }
            }
        }
    }
}