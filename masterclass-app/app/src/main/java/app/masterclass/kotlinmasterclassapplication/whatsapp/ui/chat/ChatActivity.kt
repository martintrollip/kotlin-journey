package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private var database: DatabaseReference? = null
    private var currentUid: String? = null
    private var user: WhatsappUser? = null
    private var friend: WhatsappUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        currentUid = intent.getStringExtra(EXTRA_CURRENT_UID)
        database = FirebaseDatabase.getInstance().reference
            .child("WhatsappUsers")
            .child(currentUid!!)

        friend = intent.getParcelableExtra(EXTRA_FRIEND)
        getUser()
    }

    private fun getUser() {
        if (database != null) {
            database!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    user = dataSnapshot.getValue(WhatsappUser::class.java)
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

    companion object {
        const val EXTRA_CURRENT_UID: String = "EXTRA_CURRENT_UID"
        const val EXTRA_FRIEND = "EXTRA_FRIEND"
    }
}
