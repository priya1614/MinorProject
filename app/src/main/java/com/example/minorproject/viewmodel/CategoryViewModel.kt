package com.example.minorproject.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.model.AddCategoryModelClass
import com.example.minorproject.repository.CategoryRepo
import com.example.minorproject.view.CategoryFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class CategoryViewModel :ViewModel(),LifecycleObserver {


    internal fun getcategory(): MutableLiveData<ArrayList<AddCategoryModelClass>> {
     var CategoryRepo=CategoryRepo()
        return CategoryRepo.getcategorydata()



    }
}

