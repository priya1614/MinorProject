package com.example.minorproject.viewmodel

import android.accounts.AuthenticatorException
import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.*
import com.example.minorproject.repository.LoginRepo
import com.example.minorproject.util.ValidationUtils
import com.example.minorproject.util.isNetworkAvailable
import com.example.minorproject.view.MainActivity
import java.lang.Exception

class LoginViewModel() :ViewModel(),LifecycleObserver{


    private var Message=MutableLiveData<Boolean>()
    fun onSigninClicked(email: String, pwd: String):MutableLiveData<Boolean> {


        var LoginRepo = LoginRepo()
            Message = LoginRepo.signIn(email, pwd)
           return Message
        }

    }

