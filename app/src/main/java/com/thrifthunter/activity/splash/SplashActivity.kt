package com.thrifthunter.activity.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.thrifthunter.activity.LauncherActivity
import com.thrifthunter.R
import com.thrifthunter.tools.ViewModelFactory
import com.thrifthunter.activity.main.MainActivity
import com.thrifthunter.tools.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {
    private lateinit var splashViewModel : SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            UserPreference.getInstance(dataStore).getItems().asLiveData().observe(this) { userData ->
                val getToken = userData.token

                Log.e("Token", getToken)

                splashViewModel = ViewModelProvider(
                    this,
                    ViewModelFactory(UserPreference.getInstance(dataStore), getToken)
                )[SplashViewModel::class.java]

                splashViewModel.getItems().observe(this) { user ->
                    if (user.token == "") {
                        startActivity(Intent(this, LauncherActivity::class.java))
                        finish()
                    }
                    else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }
        },1000)
    }
}


