package com.example.minorproject.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minorproject.R
import com.example.minorproject.util.ValidationUtils
import com.example.minorproject.util.isNetworkAvailable
import com.example.minorproject.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener ,LifecycleOwner{

    val fm = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        btn_signup.setOnClickListener(this)
        btn_login.setOnClickListener (this)
    }

    private fun goToSignupScreen() {
        val SignUpFragment: Fragment = SignUpFragment()
        val ft = fm.beginTransaction()
        ft.add(R.id.cl, SignUpFragment)
        ft.addToBackStack("frag1")
        ft.commit()
    }
     fun goToBottomNavigation() {
        val BottomNavigationFragment: Fragment = BottomNavigationFragment()
         val ft = fm.beginTransaction()
        ft.add(R.id.cl, BottomNavigationFragment)
        ft.addToBackStack("frag10")

        ft.commit()

    }

    override fun onClick(view: View?) {
        if (view == btn_signup)
            goToSignupScreen()
        else if (view == btn_login) {


            if(!applicationContext.isNetworkAvailable())
            {
                Toast.makeText(applicationContext,"No Internet Connection",Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(input_email.text.toString())) {
                // Email field is blank
                input_email.error = "email can not be null"

            } else if(!ValidationUtils.isValidEmail(input_email.text.toString())) {
                // Invalid email id
                input_email.error= "Please enter correct email"

            } else if(TextUtils.isEmpty(input_password.text.toString())) {
                // Empty password
                input_password.error = "password can not be null"

            }
          else
                progress_Bar_login.setVisibility(View.VISIBLE)
            gotoObserver()
        }
    }
    fun gotoObserver()
    {
        val mViewModel=ViewModelProvider(this)[LoginViewModel::class.java]
        mViewModel.onSigninClicked(input_email.text.toString(), input_password.text.toString()).observe(this, Observer { Boolean->
            if(Boolean==true) {
                progress_Bar_login.setVisibility(View.INVISIBLE)

                goToBottomNavigation()
            }
            else{
                Toast.makeText(this,"Email address or password is wrong",Toast.LENGTH_SHORT).show()
            }
    })}

    }



