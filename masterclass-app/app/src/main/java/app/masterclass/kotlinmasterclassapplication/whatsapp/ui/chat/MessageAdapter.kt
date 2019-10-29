package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappMessage
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

/**
 * @author Martin Trollip
 * @since 2019/07/19 09:47
 */
class MessageAdapter(
    val currentUser: WhatsappUser,
    val friend: WhatsappUser,
    firebaseRecyclerOptions: FirebaseRecyclerOptions<WhatsappMessage>) : FirebaseRecyclerAdapter<WhatsappMessage, MessageViewHolder>(firebaseRecyclerOptions) {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_received, parent, false)
        Log.d("meh", "created new vh")
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, message: WhatsappMessage) {
        Log.d("meh", "binding new vh " + message.text)
        if (currentUser.uid == message.id) {
            holder.setSent(message)
        } else {
            holder.setReceived(message)
        }
    }
}