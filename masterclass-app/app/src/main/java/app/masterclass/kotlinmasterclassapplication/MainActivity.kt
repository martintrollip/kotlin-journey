package app.masterclass.kotlinmasterclassapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtHello.setOnClickListener {
            val intent = Intent(this, AnotherActivity::class.java)
            intent.putExtra("Name", "Martin")
            startActivity(intent)
        }
    }
}
