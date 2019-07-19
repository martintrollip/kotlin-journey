package app.masterclass.kotlinmasterclassapplication.whatsapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.*

/**
 * @author Martin Trollip
 * @since 2019/07/19 09:50
 */
class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setFriend(friend: WhatsappUser) {
        Picasso
            .with(itemView.context).load(friend.image)
            .placeholder(itemView.context.getDrawable(R.drawable.ic_profile))
            .into(itemView.imgUser)

        itemView.txtUsername.text = friend.displayName
        itemView.txtStatus.text = friend.status
    }
}