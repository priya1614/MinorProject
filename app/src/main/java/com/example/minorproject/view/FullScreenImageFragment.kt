package com.example.minorproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minorproject.R
import com.example.minorproject.viewmodel.FullScreenImageViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.f_full_screen_image.*

class  FullScreenImageFragment :Fragment() {
    var category_id:String?=null
    var categoryimage_id:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.f_full_screen_image, container, false)
        category_id=arguments?.getString("id1")
        categoryimage_id=arguments?.getString("id2")
        val mViewModel= ViewModelProvider(this)[FullScreenImageViewModel::class.java]
        mViewModel.FullImage(category_id!!,categoryimage_id!!).observe(this, Observer {

            Picasso.get().load(it)
                    .into(ac_imageview_fullscreen)

        })
        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category_id=arguments?.getString("id1")
        categoryimage_id=arguments?.getString("id2")
        delete_image.setOnClickListener {
        val mViewModel= ViewModelProvider(this)[FullScreenImageViewModel::class.java]
        mViewModel.Delete(category_id!!,categoryimage_id!!).observe(this, Observer {
            if(it)
            {
                (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()
                gotoCategoryImageFragement()
            }

        })}}
        fun gotoCategoryImageFragement()
        {

            val CategoryDetail: Fragment = CategoryDetailFragment()
            val bundle = Bundle()
            bundle.putString("id", category_id)
            CategoryDetail.arguments = bundle
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, CategoryDetail).commit()
        }
        }


