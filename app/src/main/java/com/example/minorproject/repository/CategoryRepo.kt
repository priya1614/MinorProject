package com.example.minorproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.minorproject.model.AddCategoryModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryRepo {
    var category= MutableLiveData<ArrayList<AddCategoryModelClass>>()
        private lateinit var auth: FirebaseAuth
        internal fun getcategorydata(): MutableLiveData<ArrayList<AddCategoryModelClass>> {
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
