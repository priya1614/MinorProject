package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.timeline.*

class TimelineFragment: Fragment(), LifecycleOwner {
    var TimeLineAdapter: TimeLineAdapter? = null
    var viewModel = TimeLineViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.timeline, container, false)

        timeline_recyclerview?.setHasFixedSize(true)
        viewModel = ViewModelProvider(this)[TimeLineViewModel::class.java]
        viewModel.getTimeline().observe(this, Observer { arraylist ->

            TimeLineAdapter = context?.let { TimeLineAdapter(it, arraylist!!) }
            timeline_recyclerview?.layoutManager = LinearLayoutManager(context)

            timeline_recyclerview!!.adapter = TimeLineAdapter

        })

        return v
    }
}