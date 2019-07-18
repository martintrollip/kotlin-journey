package app.masterclass.kotlinmasterclassapplication.whatsapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.dashboard.ChatsFragment
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.dashboard.FriendsFragment

/**
 * @author Martin Trollip
 * @since 2019/07/18 07:19
 */
class SectionPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FriendsFragment()
            else -> ChatsFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Friends"
            else -> "Chats"
        }
    }
}