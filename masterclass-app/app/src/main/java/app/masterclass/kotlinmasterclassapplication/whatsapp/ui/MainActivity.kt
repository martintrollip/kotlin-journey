package app.masterclass.kotlinmasterclassapplication.whatsapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.dashboard.DashboardActivity
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.login.LoginActivity
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_whatsapp_main.*

class MainActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var authListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_main)
        checkUserAuthentication()
        setupUI()
    }

    private fun checkUserAuthentication() {
        authListener = FirebaseAuth.AuthStateListener {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (authListener != null) {
            auth.addAuthStateListener(authListener!!)
        }
    }

    override fun onStop() {
        super.onStop()

        if (authListener != null) {
            auth.removeAuthStateListener(authListener!!)
        }
    }

    private fun setupUI() {
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
