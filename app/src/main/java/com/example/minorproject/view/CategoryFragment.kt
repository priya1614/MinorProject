package com.example.minorproject.view

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
import com.example.minorproject.R
import com.example.minorproject.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.f_categorylayout.*


class CategoryFragment : Fragment(),LifecycleOwner{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.f_categorylayout, container, false)

        recyclerview?.setHasFixedSize(true)

        val CategoryViewModel=ViewModelProvider(this)[CategoryViewModel::class.java]
        CategoryViewModel.getcategory().observe(this, Observer{arraylist->

                val CategoryAdapter = context?.let { CategoryAdapter(it, arraylist!!) }
                val gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
                recyclerview?.layoutManager = gridLayoutManager
                recyclerview?.adapter = CategoryAdapter

              })
        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category_floating_button.setOnClickListener {
            val AddCategory:Fragment=AddCategoryFragment()
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,AddCategory).addToBackStack("frag3").commit()
        }
    }
}

