package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.chat

import android.support.v7.widget.RecyclerView
import android.view.View
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappMessage
import kotlinx.android.synthetic.main.item_chat_received.view.*

/**
 * @author Martin Trollip
 * @since 2019/07/19 09:50
 */
class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setSent(message: WhatsappMessage) {
        itemView.name.setTextColor(itemView.resources.getColor(R.color.colorPrimary))
        set(message)
    }

    fun setReceived(message: WhatsappMessage) {
        itemView.name.setTextColor(itemView.resources.getColor(R.color.colorAccent))
        set(message)
    }

    private fun set(message: WhatsappMessage) {
        itemView.name.text = message.name
        itemView.message.text = message.text
    }
}