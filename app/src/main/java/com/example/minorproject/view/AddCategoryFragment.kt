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
import com.example.minorproject.viewmodel.AddCategoryViewModel
import kotlinx.android.synthetic.main.f_addcategory.*


class AddCategoryFragment : Fragment(),LifecycleOwner {

    private var filePath: Uri? = null
    companion object{
        var PICK_IMAGE_REQUEST=71
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.f_addcategory, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ac_addimage.setOnClickListener { launchGallery() }
        add_category_button.setOnClickListener {
            progress_Bar_addcategory.setVisibility(View.VISIBLE)
            gotoObserver()
        }

    }
    fun gotoObserver()
    {
        val mViewModel= ViewModelProvider(this)[AddCategoryViewModel::class.java]
        mViewModel.onAddClick(filePath!!,ac_title.text.toString()).observe(this, Observer { Boolean->
            if(Boolean==true) {
                progress_Bar_addcategory.setVisibility(View.INVISIBLE)
           gotoCategoryFragment()
            }
        })}
    fun gotoCategoryFragment()
    {
        val category: Fragment = CategoryFragment()

        (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, category).setBreadCrumbTitle("Category").addToBackStack("frag7").commit()


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
            categoryimage_category.setImageURI(filePath)

        }
    }}




