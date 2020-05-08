package com.example.minorproject.repository

import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.minorproject.R
import com.example.minorproject.view.CategoryFragment
import com.example.minorproject.view.MainActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class AddCategoryRepo {
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference
    fun addCategory(filePath:Uri,Title:String):MutableLiveData<Boolean>{
        var id=MutableLiveData<Boolean>()
    auth = FirebaseAuth.getInstance()
    mStorageRef = FirebaseStorage.getInstance().getReference()
    if(filePath != null){
        val ref = mStorageRef.child("category image/" +auth.currentUser!!.uid )
        val uploadTask = ref.putFile(filePath)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: MutableMap<String, Any> = HashMap()
                val downloadUri = task.result
                val uri = downloadUri.toString()
                user["Title"] = Title
                user["imageUrl"] = uri

                val db = FirebaseFirestore.getInstance().collection("category").document(auth.currentUser!!.uid)
                db.collection("category details").document().set(user)
                        .addOnSuccessListener { documentReference ->
                            id.value=true
                            // Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                               }

            }}
    }else{
        id.value=false
    }
return id
}}
