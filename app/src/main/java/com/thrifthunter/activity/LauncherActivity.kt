package com.thrifthunter.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.thrifthunter.auth.LoginActivity
import com.thrifthunter.auth.RegistrationActivity
import com.thrifthunter.databinding.ActivityLauncherBinding

private lateinit var binding: ActivityLauncherBinding

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { goToLogin() }
        binding.btnRegister.setOnClickListener { goToRegister() }

        setView()
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
        supportActionBar?.hide()
    }

    private fun goToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }

    private fun goToRegister() {
        val i = Intent(this, RegistrationActivity::class.java)
        startActivity(i)
    }
}

