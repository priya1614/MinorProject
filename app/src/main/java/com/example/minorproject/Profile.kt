package com.example.minorproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException



class Profile : Fragment()
{
    private lateinit var pname:TextView
    private lateinit var pemail:TextView
    var PICK_IMAGE_REQUEST=73
    private var filePath: Uri? = null
    private var pbtn: Button? = null
    private var pbtn2: Button? = null
    private lateinit var auth: FirebaseAuth
    private var imageview: ImageView? = null
    private lateinit var mStorageRef: StorageReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("user").document(auth.currentUser!!.uid)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            val email: String? = documentSnapshot?.getString("email")
            val name: String? = documentSnapshot?.getString("name")
            val imagurl: String? = documentSnapshot?.getString("imageUrl")
           // Log.d("vaal","${imagurl}")

            pemail = view?.findViewById<View>(R.id.profile_email) as TextView
            pemail.setText(email).toString()
            pname = view?.findViewById<View>(R.id.profile_name) as TextView
            pname.setText(name).toString()

            imageview = view?.findViewById<ImageView>(R.id.iv2) as ImageView
            Glide.with(this).load(imagurl).apply(RequestOptions.circleCropTransform())
                    .into(imageview!!)

        }
        return inflater.inflate(R.layout.profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mStorageRef = FirebaseStorage.getInstance().getReference()
        pbtn = view.findViewById<View>(R.id.btn_logout) as Button
        auth = FirebaseAuth.getInstance()
        pbtn!!.setOnClickListener {
            auth.signOut()
            val BottomNavigation:Fragment=BottomNavigation()
            val MainActivity:Activity=MainActivity()
            (context as MainActivity).supportFragmentManager.beginTransaction().remove(BottomNavigation).commit()
            val intent = Intent(this.context, MainActivity::class.java)
            startActivity(intent)
        }
        pbtn2 = view.findViewById(R.id.btn_changeprofile) as Button
        imageview = view.findViewById<ImageView>(R.id.iv2) as ImageView
        imageview!!.setOnClickListener {
            launchGallery()


        }
        pbtn2!!.setOnClickListener { addphoto() }
    }
        fun addphoto()
        {
            auth = FirebaseAuth.getInstance()
           // Log.d("val","Kuchb")
          //  Log.d("val","${filePath}")

        mStorageRef = FirebaseStorage.getInstance().getReference()
        if(filePath != null) {

            val ref = mStorageRef.child("uploads/" + auth.currentUser!!.uid)
            ref.putFile(filePath!!)
          .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val db = FirebaseFirestore.getInstance()
                    val documentReference = db.collection("user").document(auth.currentUser!!.uid)
                    documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                        val email: String? = documentSnapshot?.getString("email")
                        val name: String? = documentSnapshot?.getString("name")
                        val password:String?=documentSnapshot?.getString("password")

                    val user: MutableMap<String, Any> = HashMap()
                    val downloadUri = task.result
                    val uri=downloadUri.toString()
                    user["name"] = name.toString()
                    user["email"] = email.toString()
                    user["password"] = password.toString()
                    user["imageUrl"] = uri

                    db.collection("user").document(auth.currentUser!!.uid).set(user as Map<String, Any>)

                            .addOnSuccessListener { documentReference ->
                                //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
        }}}}}

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data

        }
    }
}




