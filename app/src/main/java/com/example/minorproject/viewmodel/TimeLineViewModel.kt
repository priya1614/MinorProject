package com.example.minorproject.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.model.AddCategoryModelClass
import com.example.minorproject.model.TimeLineModelClass
import com.example.minorproject.repository.CategoryRepo
import com.example.minorproject.repository.TimelineRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TimeLineViewModel : ViewModel(),LifecycleObserver
{
    internal fun getTimeline(): MutableLiveData<ArrayList<TimeLineModelClass>>{
        var TimelineRepo= TimelineRepo()
        return TimelineRepo.getTimeline()


    }
    }

