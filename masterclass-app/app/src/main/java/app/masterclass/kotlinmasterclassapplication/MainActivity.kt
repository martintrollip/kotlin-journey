package app.masterclass.kotlinmasterclassapplication

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import app.masterclass.kotlinmasterclassapplication.ui.FirebaseActivity
import app.masterclass.kotlinmasterclassapplication.ui.PersonActivity
import app.masterclass.kotlinmasterclassapplication.ui.RecipeActivity
import app.masterclass.kotlinmasterclassapplication.ui.VolleyActivity
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val PREFERENCES_NAME = "PREFERENCES"
    private val PREFERENCE_NAME = "PREF_NAME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        passDataUsingIntentExtra()
        setHelloMessage()
        setSharedPreferences()

        btnSave.setOnClickListener {
            setSharedPreferences()
        }

        btnVolley.setOnClickListener {
            startActivity(Intent(this, VolleyActivity::class.java))
        }

        btnRecipe.setOnClickListener {
            startActivity(Intent(this, RecipeActivity::class.java))
        }

        btnFire.setOnClickListener {
            startActivity(Intent(this, FirebaseActivity::class.java))
        }

        btnWhatsapp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setHelloMessage() {
        txtHello.text = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).getString(PREFERENCE_NAME, "")
    }

    private fun setSharedPreferences() {
        if (!TextUtils.isEmpty(edtName.text.toString())) {
            val sharedPreference = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString(PREFERENCE_NAME, edtName.text.toString())
            editor.apply()
        }
        setHelloMessage()
    }

    private fun passDataUsingIntentExtra() {
        txtHello.setOnClickListener {
            val intent = Intent(this, PersonActivity::class.java)
            intent.putExtra("Name", "Martin")
            startActivity(intent)
        }
    }
}
