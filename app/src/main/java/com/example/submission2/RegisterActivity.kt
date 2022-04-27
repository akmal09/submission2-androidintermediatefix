package com.example.submission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.submission2.databinding.ActivityRegisterBinding
import com.example.submission2.viewmodel.AuthViewModel
import com.example.submission2.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val authViewModel : AuthViewModel by viewModels{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)

        setListener()

        binding.registerButton.setOnClickListener{
            if(validasi()){
                showLoading(true)
                val name = binding.namaEditText.text.toString().trim()
                val email = binding.emailEditText.text.toString().trim()
                val password =binding.passwordEditText.text.toString().trim()
                authViewModel.registerLauncher(name, email, password).observe(this,{
                    if (it != null) {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        showLoading(false)
                        startActivity(intent)
                    }else{
                        showLoading(false)
                        Log.d("RegisterActivitygagal","gaagl register")
                        Toast.makeText(this, "Data sudah ada", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun validasi(): Boolean = valiName() && valiEmail() && valiPassword()

    private fun setListener() {
        with(binding) {
            namaEditText.addTextChangedListener(validasiInput(namaEditText))
            emailEditText.addTextChangedListener(validasiInput(emailEditText))
            passwordEditText.addTextChangedListener(validasiInput(passwordEditText))
        }
    }

    private fun valiName(): Boolean {
        if (binding.namaEditText.text.toString().trim().isEmpty()) {
            binding.namaEditText.error = "Jangan kosong"
            binding.namaEditText.requestFocus()
            return false
        }else{
            return true
        }
    }

    private fun valiEmail(): Boolean {
        if (binding.emailEditText.text.toString().trim().isEmpty()) {
            binding.emailEditText.error = "Jangan Kosong"
            binding.emailEditText.requestFocus()
            return false
        }else if(!ValidatorHelper.cekEmail(binding.emailEditText.text.toString())){
            binding.emailEditText.error = "Invalid email type"
            binding.emailEditText.requestFocus()
            return false
        }else{
            return true
        }
    }

    private fun valiPassword(): Boolean {
        if (binding.passwordEditText.text.toString().trim().isEmpty()) {
            binding.passwordEditText.error = "Jangan Kosong"
            binding.passwordEditText.requestFocus()
            return false
        }else if (binding.passwordEditText.text.toString().length < 6) {
            binding.passwordEditText.error = "password kurang dari 6"
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
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            when (view.id) {
                R.id.nama_edit_text ->{
                    valiName()
                }
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