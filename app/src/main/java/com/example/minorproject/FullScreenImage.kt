package com.example.minorproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.minorproject.model.AddCategoryImageModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class FullScreenImage :Fragment()
{
    var image:ImageView?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.full_screen_image, container, false)
        val args = arguments!!.getString("id1")
        val args2 = arguments!!.getString("id2")

        Log.d("val","${args}")
        Log.d("val","${args2}")

        val db = FirebaseFirestore.getInstance().collection("category image").document(args!!)
        val documentReference =db.collection("category image details").document(args2!!)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            image=v?.findViewById(R.id.ac_image3) as ImageView
            val imageUrl: String? = documentSnapshot?.getString("imageUrl")
            Log.d("val","${imageUrl}")
            Picasso.get().load(imageUrl)
                    .resize(50, 50)
                    .centerCrop().into(image)

                        }

        return v

    }

}
