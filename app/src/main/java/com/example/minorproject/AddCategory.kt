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
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException

@Suppress("DEPRECATION")
class AddCategory : Fragment() {
    private val PICK_IMAGE_REQUEST = 72
    private var filePath: Uri? = null
    private var imageview: ImageView? = null
    private var text: EditText? = null

    private var add:Button?=null
    private var addimage:Button?=null
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference
    var TAG = "val"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.addcategory, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addimage = view.findViewById<View>(R.id.ac_addimage) as Button
        add = view.findViewById<View>(R.id.floatingActionButton) as Button
       text = view.findViewById<View>(R.id.ac_title) as EditText
        imageview = view.findViewById<View>(R.id.ac_image) as ImageView
        addimage!!.setOnClickListener { launchGallery() }
        add!!.setOnClickListener {
            val title = text!!.text.toString()
            auth = FirebaseAuth.getInstance()
            mStorageRef = FirebaseStorage.getInstance().getReference();
            if(filePath != null){
                val ref = mStorageRef?.child("category image/" +auth.currentUser!!.uid )
                val uploadTask = ref?.putFile(filePath!!)

                val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: MutableMap<String, Any> = HashMap()
                        val downloadUri = task.result
                        var uri=downloadUri.toString()
                        user["Title"] = title
                        user["imageUrl"] = uri

                        val db = FirebaseFirestore.getInstance().collection("category").document(auth.currentUser!!.uid)
                        db.collection("category details").document().set(user)
                                .addOnSuccessListener { documentReference ->
                                    // Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                    var category: Fragment = Category()

                                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.cl, category).commit()

                                }

                    } else {
                        // Handle failures
                    }
                }?.addOnFailureListener{

                }
            }else{
                Toast.makeText(activity, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }

        }
    }
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
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
                imageview?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}

