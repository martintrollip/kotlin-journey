package app.masterclass.kotlinmasterclassapplication.whatsapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_whatsapp_login.*

class WhatsappLoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_login)

        btnLogin.setOnClickListener {
            login(edtEmail.text.toString().trim(), edtPassword.text.toString().trim())
        }
    }

    private fun login(email: String, password: String) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    navigateToChats()
                } else {
                    Toast.makeText(this, "Unable to login user " + task.exception, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, R.string.whatsapp_enter_all_fields, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToChats() {
        startActivity(Intent(this, WhatsappDashboardActivity::class.java))
        finish()
    }
}
