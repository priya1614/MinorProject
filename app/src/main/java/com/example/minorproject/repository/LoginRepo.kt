package com.example.minorproject.repository

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.minorproject.util.ValidationUtils
import com.example.minorproject.util.isNetworkAvailable
import com.example.minorproject.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class LoginRepo ()
{
    private lateinit var auth: FirebaseAuth
    fun signIn(email: String, pwd: String):MutableLiveData<Boolean> {
        val id=MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()


        auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {

                        val user = auth.getCurrentUser()
                         id.value=true

                    } else {
                       id.value=false
                    }
                }
       return id

    }
    }


