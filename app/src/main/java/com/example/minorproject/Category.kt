package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minorproject.model.AddCategoryModelClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth


class Category : Fragment(),LifecycleOwner{
    var recyclerView:RecyclerView?=null
    var f:FloatingActionButton?=null

    var gridLayoutManager: GridLayoutManager?=null
    private lateinit var auth: FirebaseAuth
    var AddCategoryAdapter:AddCategoryAdapter?=null

    var ArrayList=MutableLiveData<ArrayList<AddCategoryModelClass>>()
    var viewModel=CategoryViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.categorylayout, container, false)
        recyclerView = v?.findViewById(R.id.rv)
        recyclerView?.setHasFixedSize(true)

        viewModel=ViewModelProvider(this)[CategoryViewModel::class.java]
        viewModel.getcategory().observe(this, Observer{arraylist->
            AddCategoryAdapter=context?.let { AddCategoryAdapter(it, arraylist!!) }
            gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView!!.adapter=AddCategoryAdapter

        })

        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        f=view.findViewById(R.id.fab)
        f!!.setOnClickListener {
            var AddCategory:Fragment=AddCategory()
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,AddCategory).addToBackStack("frag3").commit()
        }
    }

}

