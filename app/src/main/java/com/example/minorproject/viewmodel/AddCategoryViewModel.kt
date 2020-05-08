package com.example.minorproject.viewmodel

import android.net.Uri
import android.service.quicksettings.Tile
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.repository.AddCategoryRepo
import com.example.minorproject.repository.LoginRepo

class AddCategoryViewModel :ViewModel(),LifecycleObserver{
    var Message=MutableLiveData<Boolean>()

fun onAddClick(filepath:Uri,Title:String):MutableLiveData<Boolean>
{
    var AddCategoryRepo=AddCategoryRepo()

    Message = AddCategoryRepo.addCategory(filepath, Title)
    return Message
}
}
