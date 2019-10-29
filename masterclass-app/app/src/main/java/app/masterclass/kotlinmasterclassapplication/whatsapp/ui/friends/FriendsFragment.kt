package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.friends

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.chat.ChatActivity
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.settings.SettingsActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_friends.*
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.Query

class FriendsFragment : Fragment() {

    private var query: Query? = null
    private var adapter: FriendsAdapter? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUser = FirebaseAuth.getInstance().currentUser

        query = FirebaseDatabase.getInstance().reference.child("WhatsappUsers")
        val firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<WhatsappUser>()
            .setQuery(query!!, WhatsappUser::class.java)
            .build()

        adapter =
            FriendsAdapter(firebaseRecyclerOptions)
        friendsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        friendsRecyclerView.adapter = adapter

        adapter!!.onItemClickedObserver().subscribe { friend ->
            if (friend != null) {
                if (currentUser!!.uid == friend.uid) {
                    //Myself
                    startActivity(Intent(this.context, SettingsActivity::class.java))
                } else {
                    val chatIntent = Intent(this.context, ChatActivity::class.java)
                    chatIntent.putExtra(ChatActivity.EXTRA_CURRENT_UID, currentUser!!.uid)
                    chatIntent.putExtra(ChatActivity.EXTRA_FRIEND, friend)
                    startActivity(chatIntent)
                }
            }
        }
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
