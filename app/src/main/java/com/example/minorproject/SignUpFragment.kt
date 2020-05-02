

package com.example.minorproject

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
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.f_signup.*
import kotlin.collections.HashMap


class SignUpFragment : Fragment() {
    var semail: EditText? = null
    var sname: EditText? = null
    var spass: EditText? = null
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var choose: Button? = null
    private var imageview: ImageView? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageRef: StorageReference



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.f_signup, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        semail = view.findViewById<View>(R.id.signup_email) as EditText
        sname = view.findViewById<View>(R.id.signup_name) as EditText
        spass = view.findViewById<View>(R.id.signup_password) as EditText



        imageview = view.findViewById<View>(R.id.iv) as ImageView
        btn.setOnClickListener { launchGallery() }


        mStorageRef = FirebaseStorage.getInstance().getReference()


        btn_signup2!!.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            mStorageRef = FirebaseStorage.getInstance().getReference()
            val Email2 = semail!!.text.toString()
            val password2 = spass!!.text.toString()
            val name = sname!!.text.toString()
            if (name.isEmpty()) {
                sname!!.error = "fill name"

            } else if (password2.isEmpty()) {
                spass!!.error = "password of atleast 6 character"
            } else if (Email2.isEmpty()) {
                semail!!.error = "Fill valid email address"
            } else {
                auth.createUserWithEmailAndPassword(Email2, password2)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                              //  Log.d(TAG, "createUserWithEmail:success")
                                    if(filePath != null){
                                        val ref = mStorageRef.child("uploads/" +auth.currentUser!!.uid )
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
                                                val uri=downloadUri.toString()
                                                user["name"] = name
                                                user["email"] = Email2
                                                user["password"] = password2
                                                user["imageUrl"] = uri
                                                val db = FirebaseFirestore.getInstance()
                                                db.collection("user").document(auth.currentUser!!.uid).set(user as Map<String, Any>)

                                                        .addOnSuccessListener { documentReference ->
                                                            //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                                        }
                                                (context as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()

                                            }
                                        }
                                    }else{
                                        Toast.makeText(activity, "Please Upload an Image", Toast.LENGTH_SHORT).show()
                                    }

                            } else { // If sign in fails, display a message to the user.
                               // Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(activity, "user name already exits or enter valid email and password", Toast.LENGTH_SHORT).show()
                            }

                        }
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


