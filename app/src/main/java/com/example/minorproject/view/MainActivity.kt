package com.example.minorproject.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.minorproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    var model=BottomNavigationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val email = findViewById<View>(R.id.input_email) as EditText
       val pass = findViewById<View>(R.id.input_password) as EditText
        auth = FirebaseAuth.getInstance()

        btn_signup.setOnClickListener {

            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            val SignUpFragment: Fragment = SignUpFragment()
            ft.add(R.id.cl, SignUpFragment)
            ft.addToBackStack("frag1")
            ft.commit()
        }
        btn_login.setOnClickListener {
            val Email = email.text.toString()
            val password = pass.text.toString()
            if (Email.isEmpty()) {
                email.error = "Enter valid email address"
            } else if (password.isEmpty()) {
                pass.error = "Enter password of atleast 6 character"
            } else{
                auth.signInWithEmailAndPassword(Email, password)
                        .addOnCompleteListener(this@MainActivity) { task ->
                            if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                              //  Log.d(TAG, "signInWithEmail:success")
                                Toast.makeText(this@MainActivity, "Authentication success.", Toast.LENGTH_SHORT).show()

                                val user = auth.getCurrentUser()
                                updateUI(user)
                            } else {
                                Toast.makeText(this@MainActivity, "email id or password are incorrecct",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val BottomNavigation: Fragment = BottomNavigationFragment()
        ft.replace(R.id.cl, BottomNavigation)
        ft.addToBackStack("frag1")
        ft.commit()

    }
}