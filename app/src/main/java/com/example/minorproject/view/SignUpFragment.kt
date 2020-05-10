

package com.example.minorproject.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minorproject.R
import com.example.minorproject.util.ValidationUtils
import com.example.minorproject.util.isNetworkAvailable
import com.example.minorproject.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.f_signup.*



class SignUpFragment : Fragment(),View.OnClickListener,LifecycleOwner {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_signup, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }
    private fun setListeners() {
        login.setOnClickListener(this)
        btn_back.setOnClickListener (this)
        btn_signup_signup.setOnClickListener (this)
    }
    override fun onClick(view: View?) {
        if(view==btn_back)
        {
            goToHomeScreen()
        }
        if (view == login)
            (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()
        if (view == btn_signup_signup) {

            if (!context!!.isNetworkAvailable()) {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(signup_name.text.toString())) {
                signup_name.error = "name can not be null"

            }
            if (TextUtils.isEmpty(signup_email.text.toString())) {
                signup_email.error = "email can not be null"

            } else if (!ValidationUtils.isValidEmail(signup_email.text.toString())) {
                signup_email.error = "Please enter correct email"

            } else if (TextUtils.isEmpty(signup_password.text.toString())) {
                signup_password.error = "password can not be null"

            } else
                progress_Bar_signup.setVisibility(View.VISIBLE)
                gotoSignupObserver()
        }
    }
        fun gotoSignupObserver()
        {
            val mViewModel= ViewModelProvider(this)[SignupViewModel::class.java]
            mViewModel.onSignupClicked(signup_name.text.toString(),signup_email.text.toString(), signup_password.text.toString()).observe(this, Observer { Boolean->
                if(Boolean==true) {
                    progress_Bar_signup.setVisibility(View.INVISIBLE)
                    goToHomeScreen()
                }
                else{
                    Toast.makeText(context,"userId already Exist",Toast.LENGTH_SHORT).show()
                }
            })}

    fun goToHomeScreen()
    {
        (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()
    }
    }






