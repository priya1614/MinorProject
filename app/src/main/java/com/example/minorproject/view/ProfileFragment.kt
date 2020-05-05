package com.example.minorproject.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minorproject.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.profile.*


class ProfileFragment : Fragment()
{


    private var filePath: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference
    companion object{
        var PICK_IMAGE_REQUEST=73
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("user").document(auth.currentUser!!.uid)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            val email: String? = documentSnapshot?.getString("email")
            val name: String? = documentSnapshot?.getString("name")
            val imagurl: String? = documentSnapshot?.getString("imageUrl")
           // Log.d("vaal","${imagurl}")
            if(imagurl!=null)
            {
                Glide.with(this).load(imagurl).apply(RequestOptions.circleCropTransform())
                        .into(profile_imageview)
            }



            profile_email.setText(email).toString()
            profile_name.setText(name).toString()



        }
        return inflater.inflate(R.layout.profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mStorageRef = FirebaseStorage.getInstance().getReference()
        auth = FirebaseAuth.getInstance()
        btn_logout.setOnClickListener {
            auth.signOut()
            val BottomNavigation:Fragment=BottomNavigationFragment()
            val MainActivity:Activity=MainActivity()
            (context as MainActivity).supportFragmentManager.beginTransaction().remove(BottomNavigation).commit()
            val intent = Intent(this.context, MainActivity::class.java)
            startActivity(intent)
        }
        profile_imageview
                .setOnClickListener {
            launchGallery()


        }
        btn_changeprofile.setOnClickListener { addphoto() }
    }
        fun addphoto()
        {
            auth = FirebaseAuth.getInstance()

          //  Log.d("val","${filePath}")

        mStorageRef = FirebaseStorage.getInstance().getReference()
        if(filePath != null) {

            val ref = mStorageRef.child("uploads/" + auth.currentUser!!.uid)
            val uploadTask = ref.putFile(filePath!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })
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

                    db.collection("user").document(auth.currentUser!!.uid).set(user)
                        Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform())
                                .into(profile_imageview)
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




