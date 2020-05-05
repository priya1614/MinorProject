package com.example.minorproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minorproject.R
import com.example.minorproject.viewmodel.CategoryDetailViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.f_category_detail.*

class CategoryDetailFragment:Fragment(){

    private lateinit var auth: FirebaseAuth
    var category_id:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val v = inflater.inflate(R.layout.f_category_detail, container, false)
            recyclerview_category_detail?.setHasFixedSize(true)
            auth = FirebaseAuth.getInstance()
             category_id = arguments?.getString("id")
            val CategoryDetailViewModel = ViewModelProvider(this)[CategoryDetailViewModel::class.java]
            CategoryDetailViewModel.getcategory(category_id!!).observe(this, Observer { arraylist ->
               val  CategoryDetailAdapter= context?.let { CategoryDetailAdapter(it, arraylist) }
               val gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
                recyclerview_category_detail?.layoutManager = gridLayoutManager
                recyclerview_category_detail?.adapter = CategoryDetailAdapter

            })

            return v
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category_id = arguments?.getString("id")
        category_detail_floating_button.setOnClickListener {
            val AddCategoryImage:Fragment=AddImageToCategoryFragment()
            val bundle = Bundle()
            bundle.putString("id", category_id)
            AddCategoryImage.arguments = bundle
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,AddCategoryImage).addToBackStack("frag4").commit()
        }
    }

}


