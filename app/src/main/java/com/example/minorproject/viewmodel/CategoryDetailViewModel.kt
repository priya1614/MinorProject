package com.example.minorproject.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.model.AddCategoryImageModelClass
import com.example.minorproject.model.AddCategoryModelClass
import com.example.minorproject.repository.CategoryDetailRepo
import com.example.minorproject.repository.CategoryRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryDetailViewModel :ViewModel(),LifecycleObserver
    {


        internal fun getCategoryDetail(firebaseId:String): MutableLiveData<ArrayList<AddCategoryImageModelClass>> {
            var CategoryDetailRepo= CategoryDetailRepo()
            return CategoryDetailRepo.getCategoryDetailData(firebaseId)


        }
    }
