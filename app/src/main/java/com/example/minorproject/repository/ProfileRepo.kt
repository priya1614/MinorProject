package com.example.minorproject.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.minorproject.model.ProfileModelClass
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class ProfileRepo {
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference

    var profile= MutableLiveData<ProfileModelClass>()
fun profile():MutableLiveData<ProfileModelClass>

{


    auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val documentReference = db.collection("user").document(auth.currentUser!!.uid)
    documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

        val email: String? = documentSnapshot?.getString("email")
        val name: String? = documentSnapshot?.getString("name")
        val imagurl: String? = documentSnapshot?.getString("imageUrl")
        val Profiledata=(ProfileModelClass(email,name,imagurl))
        profile.value=Profiledata
    }
    return profile
}
    fun ChangeProfilePic(filePath:Uri):MutableLiveData<ProfileModelClass>

    {
        auth = FirebaseAuth.getInstance()
        mStorageRef = FirebaseStorage.getInstance().getReference()
            val ref = mStorageRef.child("uploads/" + auth.currentUser!!.uid)
            val uploadTask = ref.putFile(filePath)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val db = FirebaseFirestore.getInstance()
                            val documentReference = db.collection("user").document(auth.currentUser!!.uid)
                            documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                                val email: String? = documentSnapshot?.getString("email")
                                val name: String? = documentSnapshot?.getString("name")
                                val password:String?=documentSnapshot?.getString("password")

                                val user: MutableMap<String, Any> = HashMap()
                                val downloadUri = task.result
                                val uri=downloadUri.toString()
                                user["name"] = name.toString()
                                user["email"] = email.toString()
                                user["password"] = password.toString()
                                user["imageUrl"] = uri

                                db.collection("user").document(auth.currentUser!!.uid).set(user)
                                val profile_data=(ProfileModelClass(email,name,uri))
                                profile.value=profile_data
                            }}}
    return profile
    }

}
