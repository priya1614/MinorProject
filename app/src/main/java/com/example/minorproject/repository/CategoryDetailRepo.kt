package com.example.minorproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.minorproject.model.AddCategoryImageModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
class CategoryDetailRepo

{
    var CategoryDetail= MutableLiveData<ArrayList<AddCategoryImageModelClass>>()

    private lateinit var auth: FirebaseAuth
    internal fun getCategoryDetailData(id2:String): MutableLiveData<ArrayList<AddCategoryImageModelClass>> {
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
                            CategoryDetail.postValue(item)

                        }
                    }
                }

        return CategoryDetail

    }
}
