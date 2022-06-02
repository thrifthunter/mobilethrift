package com.thrifthunter

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.thrifthunter.auth.LoginActivity
import com.thrifthunter.tools.UserPreference

class SplashActivity : AppCompatActivity() {
    private lateinit var mUserPreferences: UserPreference

//    override fun onCreate(savedInstanceState: Bundle?) {
//        supportActionBar?.hide()
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//        mUserPreferences = UserPreference(this)
//        val userModel = mUserPreferences.getItems()
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            if (userModel.token != "") {
//                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//                finish()
//            }
//            else {
//                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//                finish()
//            }
//        },1000)
//    }
}