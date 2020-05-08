package com.example.minorproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.minorproject.model.TimeLineModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
class TimelineRepo

{
    var category= MutableLiveData<ArrayList<TimeLineModelClass>>()

    private lateinit var auth: FirebaseAuth
    internal fun getTimeline(): MutableLiveData<ArrayList<TimeLineModelClass>> {
        auth = FirebaseAuth.getInstance()
        val arrayList:ArrayList<TimeLineModelClass> = ArrayList()
        val db = FirebaseFirestore.getInstance()
        db.collection("timeLine image").document(auth.currentUser!!.uid).collection("f_timeline").orderBy("Date", Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val date = document.data.get("Date").toString()
                            val imageUrl = document.data.get("imageUrl").toString()
                            arrayList.add(TimeLineModelClass(imageUrl,date))
                            category.postValue(arrayList)
                        }
                    }
                }

        return category

    }
}