package com.example.minorproject.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.minorproject.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.f_addcategory.*


class AddCategoryFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 72
    private var filePath: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.f_addcategory, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ac_addimage.setOnClickListener { launchGallery() }
        add_category_button.setOnClickListener {
            val Title = ac_title.text.toString()
            auth = FirebaseAuth.getInstance()
            mStorageRef = FirebaseStorage.getInstance().getReference()
            if(filePath != null){
                val ref = mStorageRef.child("category image/" +auth.currentUser!!.uid )
                val uploadTask = ref.putFile(filePath!!)

                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: MutableMap<String, Any> = HashMap()
                        val downloadUri = task.result
                        val uri = downloadUri.toString()
                        user["Title"] = Title
                        user["imageUrl"] = uri

                        val db = FirebaseFirestore.getInstance().collection("category").document(auth.currentUser!!.uid)
                        db.collection("category details").document().set(user)
                                .addOnSuccessListener { documentReference ->
                                    // Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                    val category: Fragment = CategoryFragment()

                                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, category).setBreadCrumbTitle("Category").addToBackStack("frag7").commit()

                                }

                    }}
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
        }
    }


}

