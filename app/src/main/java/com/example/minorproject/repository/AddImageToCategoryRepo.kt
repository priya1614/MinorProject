package com.example.minorproject.repository

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.minorproject.R
import com.example.minorproject.view.CategoryDetailFragment
import com.example.minorproject.view.MainActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class AddImageToCategoryRepo {
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference
    fun AddImageToCategory(filePath:Uri,CategoryImage_id:String):MutableLiveData<Boolean> {
        val booleanId=MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
        mStorageRef = FirebaseStorage.getInstance().getReference()

            val ref = mStorageRef.child("category detail image/" + CategoryImage_id)

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
                    val date = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    val formatted = date.format(formatter)
                    user["Date"] = formatted
                    user["imageUrl"] = uri

                    val db = FirebaseFirestore.getInstance()
                    db.collection("category image").document(CategoryImage_id).collection("category image details").add(user)
                            .addOnSuccessListener { DocumentReference ->
                                booleanId.value=true
                                val id = DocumentReference.id

                                db.collection("timeLine image").document(auth.currentUser!!.uid).collection("f_timeline").document(id).set(user, SetOptions.merge()).addOnSuccessListener {


                                }

                            }
                }
            }

        return booleanId

    }
}
