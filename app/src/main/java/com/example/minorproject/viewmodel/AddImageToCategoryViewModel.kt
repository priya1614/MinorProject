package com.example.minorproject.viewmodel

import android.net.Uri
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.repository.AddCategoryRepo
import com.example.minorproject.repository.AddImageToCategoryRepo

class AddImageToCategoryViewModel :ViewModel(),LifecycleObserver{
    var Message=MutableLiveData<Boolean>()
fun onAddCategoryImage(filepath: Uri, Category_Id:String):MutableLiveData<Boolean>

{
    var AddImageToCategoryRepo= AddImageToCategoryRepo()

    Message =AddImageToCategoryRepo.AddImageToCategory(filepath, Category_Id)
    return Message
}
}