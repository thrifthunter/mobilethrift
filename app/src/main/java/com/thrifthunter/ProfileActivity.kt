package com.thrifthunter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.thrifthunter.auth.RegistrationActivity
import com.thrifthunter.auth.UserModel
import com.thrifthunter.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileBinding
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profile"

        showExistingPreference()

        binding.btnSave.setOnClickListener(this)

    }

    private fun showExistingPreference() {
        populateView(userModel)
        checkForm(userModel)
    }

    private fun populateView(userModel: UserModel) {
        binding.tvName.text =
            if (userModel.name.isEmpty()) "NULL" else userModel.name
        binding.tvEmail.text =
            if (userModel.email.isEmpty()) "NULL" else userModel.email
        binding.tvPassword.text =
            if (userModel.password.isEmpty()) "NULL" else userModel.password
    }

    private fun checkForm(userModel: UserModel) {
        when {
            userModel.name.isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            else -> {
                binding.btnSave.text = getString(R.string.signup)
                isPreferenceEmpty = true
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_save) {
            val intent = Intent(this@ProfileActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}