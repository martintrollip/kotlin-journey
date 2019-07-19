package app.masterclass.kotlinmasterclassapplication.whatsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

/**
 * @author Martin Trollip
 * @since 2019/07/19 09:47
 */
class FriendsAdapter(firebaseRecyclerOptions: FirebaseRecyclerOptions<WhatsappUser>) :
    FirebaseRecyclerAdapter<WhatsappUser, FriendsViewHolder>(firebaseRecyclerOptions) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): FriendsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int, friend: WhatsappUser) {
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Clicked $position", Toast.LENGTH_LONG).show()
        }
        holder.setFriend(friend)
    }
}