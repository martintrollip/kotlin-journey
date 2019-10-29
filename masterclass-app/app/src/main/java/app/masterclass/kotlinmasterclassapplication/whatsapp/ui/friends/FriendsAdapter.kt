package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.model.WhatsappUser
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author Martin Trollip
 * @since 2019/07/19 09:47
 */
class FriendsAdapter(firebaseRecyclerOptions: FirebaseRecyclerOptions<WhatsappUser>) :
    FirebaseRecyclerAdapter<WhatsappUser, FriendsViewHolder>(firebaseRecyclerOptions) {

    private val publishSubject = PublishSubject.create<WhatsappUser>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): FriendsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int, friend: WhatsappUser) {
        holder.itemView.setOnClickListener {
            publishSubject.onNext(friend)
        }
        holder.setFriend(friend)
    }

    public fun onItemClickedObserver(): Observable<WhatsappUser> {
        return publishSubject.hide()
    }
}