package com.example.minorproject.viewmodel

import android.net.Uri
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.model.ProfileModelClass
import com.example.minorproject.repository.ProfileRepo

class ProfileViewModel :ViewModel(),LifecycleObserver{

fun profiledata():MutableLiveData<ProfileModelClass>
{
    val ProfileRepo=ProfileRepo()
    val profileModelClass=ProfileRepo.profile()
    return profileModelClass

}
    fun PropicPicChangedata(filepath:Uri):MutableLiveData<ProfileModelClass>
    {
        val ProfileRepo=ProfileRepo()
        val ProfileData=ProfileRepo.ChangeProfilePic(filepath)
        return ProfileData

    }
}