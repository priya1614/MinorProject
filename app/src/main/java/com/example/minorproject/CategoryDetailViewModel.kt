package com.example.minorproject

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.model.AddCategoryImageModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryDetailViewModel :ViewModel(),LifecycleObserver
    {
        var category2= MutableLiveData<ArrayList<AddCategoryImageModelClass>>()

        private lateinit var auth: FirebaseAuth


        internal fun getcategory(id2:String): MutableLiveData<ArrayList<AddCategoryImageModelClass>> {
            auth = FirebaseAuth.getInstance()

            val item: ArrayList<AddCategoryImageModelClass> = ArrayList()
            val db = FirebaseFirestore.getInstance()
            db.collection("category image").document(id2).collection("category image details").get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result!!) {
                                val id = document.id
                                val imageUrl = document.data.get("imageUrl").toString()
                                item.add(AddCategoryImageModelClass(imageUrl,id,id2))
                                category2.postValue(item)

                            }
                        }
                    }

            return category2

        }
    }
