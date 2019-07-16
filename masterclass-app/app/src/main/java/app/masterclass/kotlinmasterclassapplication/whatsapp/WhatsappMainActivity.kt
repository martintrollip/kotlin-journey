package app.masterclass.kotlinmasterclassapplication.whatsapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.masterclass.kotlinmasterclassapplication.R
import kotlinx.android.synthetic.main.activity_whatsapp_main.*

class WhatsappMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_main)

        btnLogin.setOnClickListener{
            startActivity(Intent(this, WhatsappLoginActivity::class.java))
        }

        btnRegister.setOnClickListener{
            startActivity(Intent(this, WhatsappRegisterActivity::class.java))
        }
    }
}
