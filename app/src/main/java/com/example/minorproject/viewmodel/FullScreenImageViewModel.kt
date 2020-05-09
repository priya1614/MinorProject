package com.example.minorproject.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.repository.FullScreenImageRepo

class FullScreenImageViewModel:ViewModel(),LifecycleObserver

{
    var Boolean=MutableLiveData<Boolean>()
var imageUrl=MutableLiveData<String>()
    fun FullImage(categoryFirebaseId:String,categoryimageFirebaseId:String):MutableLiveData<String>
    {
        val FullScreenImageRepo=FullScreenImageRepo()
        imageUrl=FullScreenImageRepo.FullSCreenImage(categoryFirebaseId,categoryimageFirebaseId)
        return imageUrl
    }
    fun Delete(categoryFirebaseId:String,categoryimageFirebaseId:String):MutableLiveData<Boolean>

    {
        val FullScreenImageRepo=FullScreenImageRepo()
        Boolean=FullScreenImageRepo.DeleteImage(categoryFirebaseId,categoryimageFirebaseId)
        return Boolean
    }

}