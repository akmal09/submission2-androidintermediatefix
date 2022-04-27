package com.example.submission2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.submission2.ValidatorHelper.cekEmail
import com.example.submission2.databinding.ActivityLoginBinding
import com.example.submission2.model.LoginSession
import com.example.submission2.repopaging.Result
import com.example.submission2.viewmodel.AuthViewModel
import com.example.submission2.viewmodel.HomeViewModel
import com.example.submission2.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val authViewModel : AuthViewModel by viewModels{
        factory
    }
    private lateinit var loginSession: LoginSession
    private lateinit var mSessionPreference: SessionPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()
        mSessionPreference = SessionPreference(this)
        showLoading(false)

        setUpLanguage()

        if (mSessionPreference.getSession().name != "") {
            val newLoginSession= mSessionPreference.getSession()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            intent.putExtra(HomeActivity.EXTRA_RESULT, newLoginSession)
            startActivity(intent)
        }

        setListener()

        binding.loginButton.setOnClickListener{
            if (validasi()) {
                showLoading(true)
                val email = binding.emailEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString().trim()
                authViewModel.loginLauncher(email, password).observe(this,{
                    if (it != null) {
                        if (it.error == true) {
                            showLoading(false)
                            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()

                        }else{
                            saveSession(it.loginResult.name,it.loginResult.token,it.loginResult.userId)
                        }
                    }else{
                        showLoading(false)
                        Toast.makeText(this, "Email atau Password anda salah", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        binding.registerIntent.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpLanguage() {
        binding.setLanguage.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun playAnimation() {
        val email = ObjectAnimator.ofFloat(binding.email, View.ALPHA, 1f).setDuration(500)
        val emailEditText = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.password, View.ALPHA, 1f).setDuration(500)
        val passwordEditText = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val loginButton = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.registerIntent, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playTogether(email, emailEditText)
            startDelay = 500
        }.start()

        AnimatorSet().apply {
            playTogether(password, passwordEditText)
            startDelay = 500
        }.start()

        AnimatorSet().apply{
            playSequentially(
                loginButton,
                register
            )
            startDelay = 500
        }.start()
        ObjectAnimator.ofFloat(binding.registerIntent, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun saveSession(name: String, token: String, userId: String) {
        mSessionPreference = SessionPreference(this)

        loginSession = LoginSession()
        loginSession.name = name
        loginSession.token = token
        loginSession.userId = userId

        mSessionPreference.setSession(loginSession)
        moveToHome()
    }

    private fun moveToHome() {
        mSessionPreference = SessionPreference(this)
        val newLoginSession= mSessionPreference.getSession()
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        Toast.makeText(this, "validated", Toast.LENGTH_SHORT).show()
        intent.putExtra(HomeActivity.EXTRA_RESULT, newLoginSession)
        startActivity(intent)
    }

    private fun validasi(): Boolean = valiEmail() && valiPassword()

    private fun setListener() {
        with(binding) {
            emailEditText.addTextChangedListener(validasiInput(emailEditText))
            passwordEditText.addTextChangedListener(validasiInput(passwordEditText))
        }
    }

    private fun valiEmail(): Boolean {
        if (binding.emailEditText.text.toString().trim().isEmpty()) {
            binding.emailEditText.error =  getString(R.string.emptyAlert)
            binding.emailEditText.requestFocus()
            return false
        }else if(!cekEmail(binding.emailEditText.text.toString())){
            binding.emailEditText.error =  getString(R.string.invalidEmail)
            binding.emailEditText.requestFocus()
            return false
        }else{
            return true
        }

    }

    private fun valiPassword(): Boolean {
        if (binding.passwordEditText.text.toString().trim().isEmpty()) {
            binding.passwordEditText.error =  getString(R.string.emptyAlert)
            binding.passwordEditText.requestFocus()
            return false
        }else if (binding.passwordEditText.text.toString().length < 6) {
            binding.passwordEditText.error =  getString(R.string.passworAlert)
            binding.passwordEditText.requestFocus()
            return false
        }else{
            return true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        }else{
            binding.loadingBar.visibility = View.GONE
        }
    }

    inner class validasiInput(private val view: View): TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            when (view.id) {
                R.id.email_edit_text -> {
                    valiEmail()
                }
                R.id.password_edit_text -> {
                    valiPassword()
                }
            }
        }
    }
}