package com.example.minorproject.model

import android.util.Log

class AddCategoryImageModelClass {
    var args:String?=null
    var id:String?=null
    var iconsChar:String?=null


    constructor(iconsChar: String?,id:String?,args:String?) {
        this.iconsChar = iconsChar
        this.args=args
        this.id=id
        Log.d("title4","${iconsChar}")



    }
}