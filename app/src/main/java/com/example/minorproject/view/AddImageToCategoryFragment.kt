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
import com.example.minorproject.R
import com.example.minorproject.viewmodel.AddImageToCategoryViewModel
import kotlinx.android.synthetic.main.f_add_category_image.*


class AddImageToCategoryFragment : Fragment(),View.OnClickListener,LifecycleOwner {

    private var filePath: Uri? = null
    var CategoryImage_id:String?=null
    companion object{
        var PICK_IMAGE_REQUEST=72
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_add_category_image, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CategoryImage_id = arguments?.getString("id")
        setListeners()
    }
    private fun setListeners() {
        select_category_image.setOnClickListener(this)
        add_category_image_button.setOnClickListener (this)
    }
    override fun onClick(view: View?) {
        if (view == select_category_image)
            launchGallery()
        else if (view == add_category_image_button) {
            gotoObserver()
        }}
fun gotoObserver() {
    val mViewModel= ViewModelProvider(this)[AddImageToCategoryViewModel::class.java]
    mViewModel.onAddCategoryImage(filePath!!,CategoryImage_id!!).observe(this, Observer { Boolean->
        if(Boolean==true) {
            gotoCategoryDetailImageFragment()
        }
    })}


    fun gotoCategoryDetailImageFragment() {
        val CategoryDetails: Fragment = CategoryDetailFragment()
        val Bundle = Bundle()
        Bundle.putString("id", CategoryImage_id)
        CategoryDetails.arguments = Bundle
        (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, CategoryDetails).addToBackStack("frag6").commit()

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
