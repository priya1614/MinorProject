package com.example.minorproject

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minorproject.model.AlphaChar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class Category : Fragment() {
    var recyclerView:RecyclerView?=null
    var f:FloatingActionButton?=null
    var gridLayoutManager: GridLayoutManager?=null
    var arrayList:ArrayList<AlphaChar>?=null
    var AlphaAdapter:AlphaAdapter?=null
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.categorylayout, container, false)
        recyclerView = v?.findViewById(R.id.rv)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        auth = FirebaseAuth.getInstance()

            var item: ArrayList<AlphaChar> = ArrayList()
            val db = FirebaseFirestore.getInstance()
            db.collection("category").document(auth.currentUser!!.uid).collection("category details").get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result!!) {
                                var id = document.id

                                var imageUrl = document.data.get("imageUrl").toString()
                                var title = document.data.get("Title").toString()
                                item.add(AlphaChar(imageUrl, title))
                                AlphaAdapter=AlphaAdapter(context!!,item!!)
                                recyclerView?.adapter=AlphaAdapter
                            }
                        }
                    }
        return v

    }

}

