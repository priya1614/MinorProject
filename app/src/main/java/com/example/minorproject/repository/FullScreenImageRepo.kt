package com.example.minorproject.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FullScreenImageRepo {
    private lateinit var auth: FirebaseAuth
    fun FullSCreenImage(category_id:String,categoryimage_id:String):MutableLiveData<String> {
        val imageurl=MutableLiveData<String>()
        val db = FirebaseFirestore.getInstance().collection("category image").document(category_id)
        val documentReference = db.collection("category image details").document(categoryimage_id)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            val imageUrl: String? = documentSnapshot?.getString("imageUrl")
            imageurl.value=imageUrl

        }
        return imageurl
    }
    fun DeleteImage(category_id: String,categoryimage_id: String):MutableLiveData<Boolean>
    {
        val BooleanId=MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
        FirebaseFirestore.getInstance().collection("category image").document(category_id)
                .collection("category image details").document(categoryimage_id).delete().addOnCompleteListener {
                    BooleanId.value = true
                    FirebaseFirestore.getInstance().collection("timeLine image").document(auth.currentUser!!.uid)
                            .collection("f_timeline").document(categoryimage_id).delete().addOnCompleteListener {
                         BooleanId.value=true
                            }
                }
        return BooleanId
    }
    }
