package com.example.minorproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class signup : Fragment() {
    var semail: EditText? = null
    var sname: EditText? = null
    var spass: EditText? = null
    var su2: Button? = null
    private lateinit var auth: FirebaseAuth
    var TAG = "val"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        semail = view.findViewById<View>(R.id.signup_email) as EditText
        sname = view.findViewById<View>(R.id.signup_name) as EditText
        spass = view.findViewById<View>(R.id.signup_password) as EditText
        su2 = view.findViewById<View>(R.id.btn_signup2) as Button
        auth = FirebaseAuth.getInstance()
        su2!!.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            val Email2 = semail!!.text.toString()
            val password2 = spass!!.text.toString()
            val name = sname!!.text.toString()
            if (Email2.isEmpty()) {
                semail!!.error = "Fill valid email address"
            } else if (password2.isEmpty()) {
                spass!!.error = "password of atleast 6 character"
            } else if (name.isEmpty()) {
                sname!!.error = "Enter name"
            } else {
                auth.createUserWithEmailAndPassword(Email2, password2)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                Toast.makeText(activity, "Authentication success.", Toast.LENGTH_SHORT).show()
                                val user = auth.getCurrentUser()
                                updateUI(user)
                            } else { // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                                updateUI(null)
                            }
                        }
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {}
}