package abhishekint.com.wellthytinder.app.app

import abhishekint.com.wellthytinder.app.connect.ConnectFrag
import abhishekint.com.wellthytinder.app.chat.ChatFrag
import abhishekint.com.wellthytinder.app.discover.DiscoverFrag
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
            0 -> return ConnectFrag()
            1 -> return ChatFrag()
            2 -> return DiscoverFrag()
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
            0 -> return "A"
            1 -> return "B"
            2 -> return "C"
        }
        return null
    }
}