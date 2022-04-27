package com.example.submission2

import android.util.Patterns

object ValidatorHelper {
    fun cekEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}