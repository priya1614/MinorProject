package com.example.minorproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.minorproject.model.AddCategoryImageModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class FullScreenImage :Fragment() {
    var btn: Button? = null
    var image: ImageView? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.full_screen_image, container, false)
        val args = arguments?.getString("id1")
        val args2 = arguments?.getString("id2")

        Log.d("val", "${args}")
        Log.d("val", "${args2}")

        val db = FirebaseFirestore.getInstance().collection("category image").document(args!!)
        val documentReference = db.collection("category image details").document(args2!!)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            image = v?.findViewById(R.id.ac_image3) as ImageView
            val imageUrl: String? = documentSnapshot?.getString("imageUrl")
            Log.d("val", "${imageUrl}")
            Picasso.get().load(imageUrl)
                    .into(image)

        }

        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.getString("id1")
        val args2 = arguments?.getString("id2")
        btn = view.findViewById(R.id.delete_image)
        btn!!.setOnClickListener {

            auth = FirebaseAuth.getInstance()
                val db = FirebaseFirestore.getInstance().collection("category image").document(args!!)
            val documentReference = db.collection("category image details").document(args2!!).delete().addOnCompleteListener {
                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()
                val db = FirebaseFirestore.getInstance().collection("timeLine image").document(auth.currentUser!!.uid).collection("timeline").document(args2!!).delete().addOnCompleteListener {
                    Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()



                    (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()
                    var CategoryDetail: Fragment = CategoryDetail()
                    val bundle = Bundle()
                    bundle.putString("id", args)
                    CategoryDetail.arguments = bundle
                    (context as MainActivity).supportFragmentManager.beginTransaction().add(R.id.frame_container, CategoryDetail).commit()

                }

            }
        }
    }}


