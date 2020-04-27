package com.example.minorproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    var email: EditText? = null
    var pass: EditText? = null
    var login: Button? = null
    var su: Button? = null
    private lateinit var auth: FirebaseAuth

    var model=BottomNavigation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email = findViewById<View>(R.id.input_email) as EditText
        pass = findViewById<View>(R.id.input_password) as EditText
        login = findViewById<View>(R.id.btn_login) as Button
        su = findViewById<View>(R.id.btn_signup) as Button
        auth = FirebaseAuth.getInstance()

        su!!.setOnClickListener {

            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            val signup: Fragment = SignUp()
            ft.add(R.id.frame, signup)
            ft.addToBackStack("frag1")
            ft.commit()
        }
        login!!.setOnClickListener {
            val Email = email!!.text.toString()
            val password = pass!!.text.toString()
            if (Email.isEmpty()) {
                email!!.error = "Enter valid email address"
            } else if (password.isEmpty()) {
                pass!!.error = "Enter password of atleast 6 character"
            } else{
                auth.signInWithEmailAndPassword(Email, password)
                        .addOnCompleteListener(this@MainActivity) { task ->
                            if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                              //  Log.d(TAG, "signInWithEmail:success")
                                Toast.makeText(this@MainActivity, "Authentication success.", Toast.LENGTH_SHORT).show()

                                val user = auth.getCurrentUser()
                                updateUI(user)
                            } else { // If sign in fails, display a message to the user.
                              //  Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(this@MainActivity, "email id or password are incorrecct",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    private fun updateUI(user: FirebaseUser?) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val BottomNavigation: Fragment = BottomNavigation()
        ft.replace(R.id.frame, BottomNavigation)
        ft.addToBackStack("frag1")
        ft.commit()

    }
}