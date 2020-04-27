package com.example.minorproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minorproject.model.AddCategoryImageModelClass
import com.example.minorproject.model.AddCategoryModelClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryDetail:Fragment(){
    var recyclerView: RecyclerView?=null
    var f: FloatingActionButton?=null
    var gridLayoutManager: GridLayoutManager?=null
    var arrayList:ArrayList<AddCategoryImageModelClass>?=null
    var AddCategoryImageAdapter:AddCategoryImageAdapter?=null
    private lateinit var auth: FirebaseAuth
    var viewModel2=CategoryDetailViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            var v = inflater.inflate(R.layout.category_detail, container, false)
            recyclerView = v?.findViewById(R.id.rv2)
            recyclerView?.setHasFixedSize(true)
            auth = FirebaseAuth.getInstance()
            val args = arguments?.getString("id")
            viewModel2 = ViewModelProvider(this)[CategoryDetailViewModel::class.java]
            viewModel2.getcategory(args!!).observe(this, Observer { arraylist ->
                AddCategoryImageAdapter = context?.let { AddCategoryImageAdapter(it, arraylist!!) }
                gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
                recyclerView?.layoutManager = gridLayoutManager
                recyclerView!!.adapter = AddCategoryImageAdapter

            })

            return v
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var args = arguments?.getString("id")
        Log.d("val1","${args}")
        f=view.findViewById(R.id.fab2)
        f!!.setOnClickListener {
            var AddCategoryImage:Fragment=AddCategoryImage()
            val bundle = Bundle()
            bundle.putString("id", args)
            AddCategoryImage.arguments = bundle
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,AddCategoryImage).addToBackStack("frag4").commit()
        }
    }

}


