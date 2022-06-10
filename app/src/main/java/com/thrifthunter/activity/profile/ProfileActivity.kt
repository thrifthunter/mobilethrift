package com.thrifthunter.activity.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.thrifthunter.R
import com.thrifthunter.tools.ViewModelFactory
import com.thrifthunter.auth.RegistrationActivity
import com.thrifthunter.tools.UserModel
import com.thrifthunter.databinding.ActivityProfileBinding
import com.thrifthunter.tools.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var profileViewModel : ProfileViewModel
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
        populateView()
        checkForm()
    }

    private fun populateView() {
        UserPreference.getInstance(dataStore).getItems().asLiveData().observe(this) { userData ->
            val getToken = userData.token

            Log.e("Token", getToken)

            profileViewModel = ViewModelProvider(
                this,
                ViewModelFactory(UserPreference.getInstance(dataStore), getToken)
            )[ProfileViewModel::class.java]

            profileViewModel.getItems().observe(this) { user ->
                binding.tvName.text =
                    if (user.name.isEmpty()) "NULL" else user.name
                binding.tvEmail.text =
                    if (user.email.isEmpty()) "NULL" else user.email
                binding.tvPassword.text =
                    if (user.password.isEmpty()) "NULL" else user.password
            }
        }
    }

    private fun checkForm() {

        UserPreference.getInstance(dataStore).getItems().asLiveData().observe(this) { userData ->
            val getToken = userData.token

            Log.e("Token", getToken)

            profileViewModel = ViewModelProvider(
                this,
                ViewModelFactory(UserPreference.getInstance(dataStore), getToken)
            )[ProfileViewModel::class.java]

            profileViewModel.getItems().observe(this) { user ->
                when {
                    user.name.isNotEmpty() -> {
                        binding.btnSave.text = getString(R.string.change)
                        isPreferenceEmpty = false
                    }
                    else -> {
                        binding.btnSave.text = getString(R.string.signup)
                        isPreferenceEmpty = true
                    }
                }
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