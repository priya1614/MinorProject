package com.example.minorproject.util

import android.content.Context
import android.net.ConnectivityManager

class ValidationUtils {
    companion object{
        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

}