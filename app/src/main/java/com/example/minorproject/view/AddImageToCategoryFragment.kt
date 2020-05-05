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
import androidx.fragment.app.Fragment
import com.example.minorproject.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.f_add_category_image.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class AddImageToCategoryFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 72
    private var filePath: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference
  var CategoryImage_id:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_add_category_image, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CategoryImage_id = arguments?.getString("id")
        select_category_image.setOnClickListener { launchGallery() }
        add_category_image_button.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            mStorageRef = FirebaseStorage.getInstance().getReference()
            if(filePath != null){
                val ref = mStorageRef.child("category detail image/" +CategoryImage_id )

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
                        val user: MutableMap<String,Any> = HashMap()

                        val downloadUri = task.result
                        val uri=downloadUri.toString()
                        val date = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        val formatted = date.format(formatter)

                        user["Date"]=formatted

                        user["imageUrl"] = uri

                        val db = FirebaseFirestore.getInstance()
                        db.collection("category image").document(CategoryImage_id!!).collection("category image details").add(user)
                                .addOnSuccessListener{DocumentReference->
                                    val id=DocumentReference.id

                                                db.collection("timeLine image").document(auth.currentUser!!.uid).collection("f_timeline").document(id).set(user, SetOptions.merge()).addOnSuccessListener {

                                                    val CategoryDetails: Fragment = CategoryDetailFragment()
                                                  //  Log.d("id", "${args}")
                                                    val Bundle = Bundle()
                                                    Bundle.putString("id",CategoryImage_id)
                                                    CategoryDetails.arguments = Bundle
                                                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, CategoryDetails).addToBackStack("frag6").commit()

                                                }






            }}}}

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
