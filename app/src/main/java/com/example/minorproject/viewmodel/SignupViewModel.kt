package com.example.minorproject.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.repository.LoginRepo
import com.example.minorproject.repository.SignupRepo

class SignupViewModel :ViewModel(),LifecycleObserver{
    private var Message= MutableLiveData<Boolean>()
    fun onSignupClicked(name:String,email: String, pwd: String): MutableLiveData<Boolean> {


        var SignupRepo = SignupRepo()
        Message = SignupRepo.SignUp(name,email, pwd)
        return Message
    }
}

