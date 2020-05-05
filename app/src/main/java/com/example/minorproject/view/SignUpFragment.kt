

package com.example.minorproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.minorproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.f_signup.*


class SignUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.f_signup, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       login.setOnClickListener {  (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()}

        mStorageRef = FirebaseStorage.getInstance().getReference()
        btn_signup_signup.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            mStorageRef = FirebaseStorage.getInstance().getReference()
            val Email = signup_email.text.toString()
            val password = signup_password.text.toString()
            val name = signup_name.text.toString()
            if (name.isEmpty()) {
                signup_name.error = "username can not be empty"

            } else if (password.isEmpty()) {
                signup_password.error = "password of atleast 6 character"
            } else if (Email.isEmpty()) {
                signup_email.error = "Fill valid email address"
            } else {
                auth.createUserWithEmailAndPassword(Email, password)
                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                val user: MutableMap<String, Any> = HashMap()
                                                user["name"] = name
                                                user["email"] = Email
                                                user["password"] = password

                                                val db = FirebaseFirestore.getInstance()
                                                db.collection("user").document(auth.currentUser!!.uid).set(user as Map<String, Any>)

                                                        .addOnSuccessListener { documentReference ->
                                                            //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                                        }
                                                (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()

                                            }
                                        }
                                    }

                            }

                        }
            }





