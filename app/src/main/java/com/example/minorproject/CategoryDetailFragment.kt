package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.category_detail.*

class CategoryDetailFragment:Fragment(){
    var gridLayoutManager: GridLayoutManager?=null
    var AddCategoryImageAdapter:CategoryDetailAdapter?=null
    private lateinit var auth: FirebaseAuth
    var viewModel2=CategoryDetailViewModel()
    var category_id:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val v = inflater.inflate(R.layout.category_detail, container, false)
            recyclerview_2?.setHasFixedSize(true)
            auth = FirebaseAuth.getInstance()
             category_id = arguments?.getString("id")
            viewModel2 = ViewModelProvider(this)[CategoryDetailViewModel::class.java]
            viewModel2.getcategory(category_id!!).observe(this, Observer { arraylist ->
                AddCategoryImageAdapter = context?.let { CategoryDetailAdapter(it, arraylist!!) }
                gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
                recyclerview_2?.layoutManager = gridLayoutManager
                recyclerview_2!!.adapter = AddCategoryImageAdapter

            })

            return v
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category_id = arguments?.getString("id")
       // Log.d("val1","${args}")
        fab2!!.setOnClickListener {
            val AddCategoryImage:Fragment=AddImageToCategoryFragment()
            val bundle = Bundle()
            bundle.putString("id", category_id)
            AddCategoryImage.arguments = bundle
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,AddCategoryImage).addToBackStack("frag4").commit()
        }
    }

}


