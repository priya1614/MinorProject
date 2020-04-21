package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class Profile : Fragment()
{
    private lateinit var pname:TextView
    private lateinit var pemail:TextView
    private var pbtn: Button? = null
    private lateinit var auth: FirebaseAuth
    private var imageview: ImageView? = null
    var TAG="val"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance().collection("user").document("userdetails")
        val documentReference = db.collection("user").document(auth.currentUser!!.uid)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            val email: String? = documentSnapshot?.getString("email")
            val name: String? = documentSnapshot?.getString("name")
            val imagurl: String? = documentSnapshot?.getString("imageUrl")
            pemail = view?.findViewById<View>(R.id.profile_email) as TextView
            pemail.setText(email).toString()
            pname = view?.findViewById<View>(R.id.profile_name) as TextView
            pname.setText(name).toString()

            imageview = view?.findViewById<View>(R.id.iv2) as ImageView
            Picasso.get().load(imagurl)
                    .resize(50, 50)
                    .centerCrop().into(imageview)

        }
        return inflater.inflate(R.layout.profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pbtn=view?.findViewById<View>(R.id.btn_logout)as Button
        auth = FirebaseAuth.getInstance()


        pbtn!!.setOnClickListener {
            auth.signOut()
            (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()
        }
    }

}




