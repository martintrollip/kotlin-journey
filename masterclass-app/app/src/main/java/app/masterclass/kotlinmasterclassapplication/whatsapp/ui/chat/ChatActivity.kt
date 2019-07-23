package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappMessage
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.friends.FriendsAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CURRENT_UID: String = "EXTRA_CURRENT_UID"
        const val EXTRA_FRIEND = "EXTRA_FRIEND"
    }

    private var database: DatabaseReference? = null
    private var currentUid: String? = null

    private var user: WhatsappUser? = null
    private var friend: WhatsappUser? = null

    private var adapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        friend = intent.getParcelableExtra(EXTRA_FRIEND)
        currentUid = intent.getStringExtra(EXTRA_CURRENT_UID)

        database = FirebaseDatabase.getInstance().reference
        getUser()
    }

    private fun getUser() {
        if (database != null) {
            val firebaseUser = database!!.child("WhatsappUsers").child(currentUid!!)
            firebaseUser.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    user = dataSnapshot.getValue(WhatsappUser::class.java)
                    initUI()
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

    private fun initUI() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.stackFromEnd = true

        val query = database!!.child("messages")
        val firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<WhatsappMessage>()
            .setQuery(query, WhatsappMessage::class.java)
            .build()

        Log.d("meh", "creating the adapter")
        adapter = MessageAdapter(user!!, friend!!, firebaseRecyclerOptions)

        chatsRecyclerView.layoutManager = linearLayoutManager
        chatsRecyclerView.adapter = adapter

        btnSend.setOnClickListener {
            database!!.child("messages").push()
                .setValue(WhatsappMessage(user!!.uid, edtMessage.text.toString(), user!!.displayName))
        }

        adapter!!.startListening()

    }

    override fun onStart() {
        super.onStart()
        if (adapter != null) {
            adapter!!.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter!!.stopListening()
        }
    }
}
