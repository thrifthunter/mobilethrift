package com.thrifthunter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.thrifthunter.auth.LoginActivity
import com.thrifthunter.auth.RegistrationActivity
import com.thrifthunter.databinding.ActivityLauncherBinding

private lateinit var binding: ActivityLauncherBinding

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener { goToLogin() }
        binding.btnLogin.setOnClickListener { goToRegister() }
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

