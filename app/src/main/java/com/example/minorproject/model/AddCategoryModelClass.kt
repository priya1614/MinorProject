package com.example.minorproject.model

import android.util.Log
import com.example.minorproject.CategoryViewModel

class AddCategoryModelClass {
    var id:String?=null
    var iconsChar:String?=null
    var alphaChar:String?=null

    constructor(iconsChar: String?, alphaChar: String?,id:String?) {
        this.iconsChar = iconsChar
        this.alphaChar = alphaChar
        this.id=id
        Log.d("title4","${iconsChar}")
        Log.d("title5","${alphaChar}")


    }

}