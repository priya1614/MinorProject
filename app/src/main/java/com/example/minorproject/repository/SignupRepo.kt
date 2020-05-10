package com.example.minorproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.minorproject.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupRepo {
    private lateinit var auth: FirebaseAuth
    fun SignUp(name:String,Email: String, password: String):MutableLiveData<Boolean> {
        val booleanid=MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(Email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: MutableMap<String, Any> = HashMap()
                        user["name"] = name
                        user["email"] = Email
                        user["password"] = password

                        val db = FirebaseFirestore.getInstance()
                        db.collection("user").document(auth.currentUser!!.uid).set(user)

                                .addOnSuccessListener { documentReference ->

                                        booleanid.value = true



                                    }
                    }
                }
        return booleanid
    }
}
