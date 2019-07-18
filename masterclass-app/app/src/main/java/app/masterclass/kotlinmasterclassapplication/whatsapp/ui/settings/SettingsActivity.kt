package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_whatsapp_settings.*
import java.io.ByteArrayOutputStream

class SettingsActivity : AppCompatActivity() {

    private var database: DatabaseReference? = null
    private var currentUser: FirebaseUser? = null
    private var user: WhatsappUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_settings)

        currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            database = FirebaseDatabase.getInstance().reference
                .child("WhatsappUsers")
                .child(currentUser!!.uid)
            displayUser()

            updateStatus.setOnClickListener {
                updateStatus()
            }

            updateImage.setOnClickListener {
                updatePhoto()
            }
        } else {
            Toast.makeText(this, "No user found", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun displayUser() {
        if (database != null) {
            database!!.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    user = dataSnapshot.getValue(WhatsappUser::class.java)

                    if (user != null) {
                        userName.text = user!!.displayName
                        editStatus.setText(user!!.status)

                        if ("default" == user!!.image) {
                            userImage.setImageResource(R.drawable.ic_profile)
                        } else {
                            Picasso
                                .with(this@SettingsActivity).load(user!!.image)
                                .placeholder(getDrawable(R.drawable.ic_profile))
                                .into(userImage)
                        }
                    } else {
                        Toast.makeText(
                            this@SettingsActivity,
                            "Oops, the data for this user was not found",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

    private fun updateStatus() {
        if (database != null) {
            editStatus.clearFocus()
            database!!.child("status").setValue(editStatus.text.toString()).addOnCompleteListener {
                Toast.makeText(this, R.string.whatsapp_status_updated, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updatePhoto() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
    }

    private fun savePhoto(uri: Uri) {
        val bytes = getBytes(uri)

        val firebasePath = FirebaseStorage.getInstance().reference
            .child("whatsapp/profile/images")
            .child("${currentUser!!.uid}.jpg")

        firebasePath.putBytes(bytes!!)
            .addOnCompleteListener { task: Task<UploadTask.TaskSnapshot> ->
                if (task.isSuccessful) {
                    savePhotoUri(firebasePath.downloadUrl)
                } else {
                    Toast.makeText(this, "Failed to upload image " + task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun savePhotoUri(uri: Task<Uri>) {
        uri.addOnSuccessListener {
            if (database != null && user != null) {
                user!!.image = it.toString()
                database!!.setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Image updated", Toast.LENGTH_LONG).show()
                        displayUser()
                    } else {
                        Toast.makeText(this, "Image not updated " + it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun getBytes(uri: Uri): ByteArray? {
        val bitmap = BitmapFactory.decodeFile(uri.path)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                savePhoto(result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(this, "Error with photo upload " + error.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
