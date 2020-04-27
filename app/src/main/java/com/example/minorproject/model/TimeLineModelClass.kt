package com.example.minorproject.model

import android.util.Log
import java.util.*

class TimeLineModelClass {

    var image:String?=null
    var date:String?=null


    constructor(image: String?,date: String?) {
        this.image = image
        this.date=date
        Log.d("date","${date}")




    }
}