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

class TimelineFragment: Fragment(), LifecycleOwner {
    var recyclerView: RecyclerView? = null
    var f: FloatingActionButton? = null
    var TimeLineAdapter: TimeLineAdapter? = null
    var viewModel = TimeLineViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.timeline, container, false)
        recyclerView = v?.findViewById(R.id.rv3)
        recyclerView?.setHasFixedSize(true)
        viewModel = ViewModelProvider(this)[TimeLineViewModel::class.java]
        viewModel.getTimeline().observe(this, Observer { arraylist ->

            TimeLineAdapter = context?.let { TimeLineAdapter(it, arraylist!!) }
            recyclerView?.layoutManager = LinearLayoutManager(context)

            recyclerView!!.adapter = TimeLineAdapter

        })

        return v
    }
}