package app.masterclass.kotlinmasterclassapplication.whatsapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.model.WhatsappUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_whatsapp_register.*

class WhatsappRegisterActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_register)

        btnRegister.setOnClickListener {
            createAccount(
                edtDisplayName.text.toString().trim(),
                edtEmail.text.toString().trim(),
                edtPassword.text.toString().trim()
            )
        }
    }

    private fun createAccount(displayName: String, email: String, password: String) {
        if (!TextUtils.isEmpty(displayName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        addUser(displayName)
                    } else {
                        Toast.makeText(this, "Failed with" + task.exception, Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, R.string.whatsapp_enter_all_fields, Toast.LENGTH_LONG).show()
        }
    }

    private fun addUser(displayName: String) {
        val uid = getCurrentUserUid()
        if (uid != null) {
            database.child("WhatsappUsers").child(uid)
                .setValue(WhatsappUser(displayName)).addOnCompleteListener { task: Task<Void> ->
                    if (task.isSuccessful) {
                        navigateToChats()
                    } else {
                        Toast.makeText(this, "Unable to write user", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Could not find user id", Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToChats() {
        startActivity(Intent(this, WhatsappDashboardActivity::class.java))
        finish()
    }

    private fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }
}