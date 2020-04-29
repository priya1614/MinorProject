package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.full_screen_image.*

class FullScreenImageFragment :Fragment() {


    var image: ImageView? = null
    private lateinit var auth: FirebaseAuth
    var category_id:String?=null
    var categoryimage_id:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.full_screen_image, container, false)


      //  Log.d("val", "${args}")
     //   Log.d("val", "${args2}")

        category_id=arguments?.getString("id1")
        categoryimage_id=arguments?.getString("id2")
        val db = FirebaseFirestore.getInstance().collection("category image").document(category_id!!)
        val documentReference = db.collection("category image details").document(categoryimage_id!!)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            image = v?.findViewById(R.id.ac_image3) as ImageView
            val imageUrl: String? = documentSnapshot?.getString("imageUrl")
        //    Log.d("val", "${imageUrl}")
            Picasso.get().load(imageUrl)
                    .into(image)

        }

        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category_id=arguments?.getString("id1")
        categoryimage_id=arguments?.getString("id2")
        delete_image.setOnClickListener {

            auth = FirebaseAuth.getInstance()
                FirebaseFirestore.getInstance().collection("category image").document(category_id!!)
          .collection("category image details").document(categoryimage_id!!).delete().addOnCompleteListener {
                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()
                  FirebaseFirestore.getInstance().collection("timeLine image").document(auth.currentUser!!.uid)
                        .collection("timeline").document(categoryimage_id!!).delete().addOnCompleteListener {
                    (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()
                    val CategoryDetail: Fragment = CategoryDetailFragment()
                    val bundle = Bundle()
                    bundle.putString("id", category_id)
                    CategoryDetail.arguments = bundle
                    (context as MainActivity).supportFragmentManager.beginTransaction().add(R.id.frame_container, CategoryDetail).commit()

                }

            }
        }
    }}


