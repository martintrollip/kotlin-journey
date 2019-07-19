package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.friends

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.adapters.FriendsAdapter
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_friends.*
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query


class FriendsFragment : Fragment() {

    private var query: Query? = null
    private var adapter: FriendsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        query = FirebaseDatabase.getInstance().reference.child("WhatsappUsers")
        val firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<WhatsappUser>()
            .setQuery(query!!, WhatsappUser::class.java)
            .build()

        adapter = FriendsAdapter(firebaseRecyclerOptions)
        friendsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        friendsRecyclerView.adapter = adapter
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
