package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.categorylayout.*


class CategoryFragment : Fragment(),LifecycleOwner{



    var gridLayoutManager: GridLayoutManager?=null

    var AddCategoryAdapter:CategoryAdapter?=null
    var viewModel=CategoryViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.categorylayout, container, false)

        recyclerview?.setHasFixedSize(true)

        viewModel=ViewModelProvider(this)[CategoryViewModel::class.java]
        viewModel.getcategory().observe(this, Observer{arraylist->
            AddCategoryAdapter=context?.let { CategoryAdapter(it, arraylist!!) }
            gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
            recyclerview?.layoutManager = gridLayoutManager
            recyclerview?.adapter=AddCategoryAdapter

        })

        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab!!.setOnClickListener {
            val AddCategory:Fragment=AddCategoryFragment()
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,AddCategory).addToBackStack("frag3").commit()
        }
    }

}

