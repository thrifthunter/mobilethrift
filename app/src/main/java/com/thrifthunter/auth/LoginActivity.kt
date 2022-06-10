package com.thrifthunter.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.thrifthunter.tools.ApiConfig
import com.thrifthunter.activity.main.MainActivity
import com.thrifthunter.R
import com.thrifthunter.tools.ViewModelFactory
import com.thrifthunter.databinding.ActivityLoginBinding
import com.thrifthunter.tools.LoginResponse
import com.thrifthunter.tools.UserModel
import com.thrifthunter.tools.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnAuth: Button = findViewById(R.id.btn_reg)
        btnAuth.setOnClickListener(this)

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
        playAnimation()
    }

    private fun setAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            when {
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
                    val service = ApiConfig().getApiService().loginUser(email, password)
                    service.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                if (responseBody != null && !responseBody.error) {
                                    loginViewModel.login(responseBody.values.token)
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                    Toast.makeText(this@LoginActivity, "Successfully Login!", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "Error!", Toast.LENGTH_SHORT).show()
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
        loginViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore), "")
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { user ->
            this.user = user
        }
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.btnReg, View.ALPHA, 1f).setDuration(500)
        val show = ObjectAnimator.ofFloat(binding.btnShow, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(signup, login, show)
        }

        AnimatorSet().apply {
            playSequentially(image, together)
            start()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_reg -> {
                val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
