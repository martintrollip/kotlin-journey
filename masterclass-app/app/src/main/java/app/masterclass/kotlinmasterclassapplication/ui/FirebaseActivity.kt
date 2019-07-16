package app.masterclass.kotlinmasterclassapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.model.Person
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_firebase.*


class FirebaseActivity : AppCompatActivity() {
    private val TAG = "FirebaseActivity"

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        hardcodedLogin.setOnClickListener {
            hardLogin()
        }

        createUser.setOnClickListener {
            createUser()
        }
    }

    private fun hardLogin() {
        //Log existing user in
        auth.signInWithEmailAndPassword("martin@test.com", "123456").addOnCompleteListener {
                task: Task<AuthResult> ->
            if(task.isSuccessful) {
                Toast.makeText(this, "User signed in", Toast.LENGTH_SHORT).show()
            }
        }

        // Write a message to the database
        //val myRef = database.getReference("message") // This just updates the same field "message"
        //myRef.setValue("Hello, World!")

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(currentUser!!.uid).push() //This adds new items
        myRef.setValue(Person("Jon", "5-800-12345", R.drawable.ic_launcher_background, 39))

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(Person::class.java)
                Log.d(TAG, "Value is: " + value?.toString())
                txtFire.text = value?.name
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun createUser() {
        val auth = FirebaseAuth.getInstance()

        val email = fireEmail.text.toString()
        val password = firePassword.text.toString()

        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    currentUser = auth.currentUser
                    txtFire.text = currentUser!!.uid
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    currentUser = null
                    txtFire.text = "Something went wrong"
                }
            }
    }

    override fun onStart() {
        super.onStart()
        currentUser = auth.currentUser

        if (currentUser != null) {
            txtFire.text = "User logged in ${currentUser!!.displayName}"
        } else {
            txtFire.text = "No user logged in"
        }
    }
}
