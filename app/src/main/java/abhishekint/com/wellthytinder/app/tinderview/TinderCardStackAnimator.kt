package abhishekint.com.wellthytinder.app.tinderview

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by abhishek on 17-06-2018.
 */
class TinderCardStackAnimator(var mCardCollection: ArrayList<View>, val context: Context?) {

    private lateinit var mLayoutsMap: HashMap<View, RelativeLayout.LayoutParams>//used to store the list of card's view
    private lateinit var baseLayout: RelativeLayout.LayoutParams

////////////////////////////////return the top view of stack.///////////////////////////////////////////////////

    private val topView: View
        get() = mCardCollection[mCardCollection.size - 1]

////////////////init block is called as soon as object is created//////////////////////////////////////////////

    init {
        setup()
    }

///////////////////////////////This func provides width and height to the individual card of stack///////////////
    private fun setup() {
        mLayoutsMap = HashMap()

        //setup basic card layout of tinderview (using framelayout)
        for (v in mCardCollection) {
            val params = v.layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT
            params.height = RelativeLayout.LayoutParams.MATCH_PARENT
            v.layoutParams = params
        }

        baseLayout = mCardCollection[0].layoutParams as RelativeLayout.LayoutParams
        baseLayout = RelativeLayout.LayoutParams(baseLayout)

        for (v in mCardCollection) {
            val params = v.layoutParams as RelativeLayout.LayoutParams
            val paramsCopy = RelativeLayout.LayoutParams(params)
            mLayoutsMap!!.put(v, paramsCopy)
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}