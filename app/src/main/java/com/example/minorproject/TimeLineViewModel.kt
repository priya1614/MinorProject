package com.example.minorproject

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.model.AddCategoryModelClass
import com.example.minorproject.model.TimeLineModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TimeLineViewModel : ViewModel(),LifecycleObserver
{
    var category= MutableLiveData<ArrayList<TimeLineModelClass>>()

    private lateinit var auth: FirebaseAuth
    internal fun getTimeline(): MutableLiveData<ArrayList<TimeLineModelClass>> {
        auth = FirebaseAuth.getInstance()
        val arrayList:ArrayList<TimeLineModelClass> = ArrayList()

        val db = FirebaseFirestore.getInstance()
        db.collection("timeLine image").document(auth.currentUser!!.uid).collection("timeline").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val date = document.data.get("Date").toString()
                            val imageUrl = document.data.get("imageUrl").toString()
                     //       Log.d("timeline val", "${imageUrl}")
                            arrayList.add(TimeLineModelClass(imageUrl,date))
                          //  Log.d("cat","${arrayList}")
                            category.postValue(arrayList)
                        }
                    }
                }

        return category

    }
}
