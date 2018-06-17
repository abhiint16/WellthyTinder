package abhishekint.com.wellthytinder.app

import abhishekint.com.wellthytinder.app.tab1.Connect
import abhishekint.com.wellthytinder.app.tab2.Chat
import abhishekint.com.wellthytinder.app.tab3.Discover
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by abhishek on 17-06-2018.
 */
class MainAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val PAGE_COUNT = 3

    // This method returns fragment to each position
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return Connect()
            1 -> return Chat()
            2 -> return Discover()
            else -> return null
        }
    }

    // This method returns the no of fragment
    override fun getCount(): Int {
        return PAGE_COUNT
    }

    // This method returns title to each fragment
    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Connect"
            1 -> return "Chat"
            2 -> return "Discover"
        }
        return null
    }
}