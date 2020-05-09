package com.example.minorproject.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minorproject.R
import com.example.minorproject.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.profile.*

class ProfileFragment : Fragment(),LifecycleOwner
{
    private var filePath: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference
    companion object{
        var PICK_IMAGE_REQUEST=73
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val mViewModel= ViewModelProvider(this)[ProfileViewModel::class.java]
        mViewModel.profiledata().observe(this, Observer {it->
            profile_name.setText(it.name)
            profile_email.setText(it.email)
            if(it.imageUrl!=null)
            {
                Glide.with(this).load(it.imageUrl).apply(RequestOptions.circleCropTransform())
                        .into(profile_imageview)
            }

        })

        return inflater.inflate(R.layout.profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_logout.setOnClickListener {
            auth.signOut()
            val BottomNavigation:Fragment=BottomNavigationFragment()
            val MainActivity:Activity=MainActivity()
            (context as MainActivity).supportFragmentManager.beginTransaction().remove(BottomNavigation).commit()
            val intent = Intent(this.context, MainActivity::class.java)
            startActivity(intent)
        }
        profile_imageview.setOnClickListener {
                 launchGallery()
        }
        btn_changeprofile.setOnClickListener { addphoto() }
           }
        fun addphoto() {
            val mViewModel= ViewModelProvider(this)[ProfileViewModel::class.java]
            mViewModel.PropicPicChangedata(filePath!!).observe(this, Observer {it->
                profile_name.setText(it.name)
                profile_email.setText(it.email)
                if(it.imageUrl!=null)
                {
                    Glide.with(this).load(it.imageUrl).apply(RequestOptions.circleCropTransform())
                            .into(profile_imageview)
                }

            })
        }
    private fun launchGallery() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data

        }
    }
}




