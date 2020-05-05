package com.example.minorproject.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.model.AddCategoryModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class CategoryViewModel :ViewModel(),LifecycleObserver
 {
    var category= MutableLiveData<ArrayList<AddCategoryModelClass>>()

    private lateinit var auth: FirebaseAuth


    internal fun getcategory(): MutableLiveData<ArrayList<AddCategoryModelClass>>{
        auth = FirebaseAuth.getInstance()
        val arrayList:ArrayList<AddCategoryModelClass> = ArrayList()

        val db = FirebaseFirestore.getInstance()
        db.collection("category").document(auth.currentUser!!.uid).collection("category details").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val id = document.id
                            val imageUrl = document.data.get("imageUrl").toString()
                            val title = document.data.get("Title").toString()
                           arrayList.add(AddCategoryModelClass(imageUrl,title,id))
                            category.postValue(arrayList)
                        }
                    }
                }

        return category

                }
    }

