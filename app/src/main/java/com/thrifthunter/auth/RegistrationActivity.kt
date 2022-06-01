package com.thrifthunter.auth

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.thrifthunter.ApiConfig
import com.thrifthunter.ViewModelFactory
import com.thrifthunter.databinding.ActivityRegistrationBinding
import com.thrifthunter.settings.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var signupViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShow.setOnClickListener {
            if(binding.btnShow.text.toString().equals("Show")){
                binding.edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.btnShow.text = "Hide"
            } else{
                binding.edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnShow.text = "Show"
            }
        }

        setView()
        setViewModel()
        setAction()
    }

    private fun setAction() {
        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.edtName.error = "Name cannot be empty!"
                }
                email.isEmpty() -> {
                    binding.edtEmail.error = "Email cannot be empty!"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.edtEmail.error = "Not an email."
                }
                password.isEmpty() -> {
                    binding.edtPassword.error = "Password cannot be empty!"
                }
                else -> {
                    signupViewModel.saveUser(UserModel(name, email, password, false, ""))
                    val service = ApiConfig().getApiService().registerUser(name, email, password)
                    service.enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                if (responseBody != null && !responseBody.error) {
                                    startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
                                    Toast.makeText(this@RegistrationActivity, "Successfully registered!", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@RegistrationActivity, "Email is already taken", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(this@RegistrationActivity, "Error. Try Again!", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        }
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

    private fun setViewModel() {
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), "")
        )[RegistrationViewModel::class.java]
    }
}
