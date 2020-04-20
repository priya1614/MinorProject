package com.example.minorproject.model

import android.util.Log

class AlphaChar {
    var iconsChar:String?=null
    var alphaChar:String?=null

    constructor(iconsChar: String?, alphaChar: String?) {
        this.iconsChar = iconsChar
        this.alphaChar = alphaChar
        Log.d("title4","${iconsChar}")
        Log.d("title5","${alphaChar}")


    }
}