package abhishekint.com.wellthytinder.app.tinderview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.RelativeLayout
import java.util.ArrayList

/**
 * Created by abhishek on 17-06-2018.
 */
class TinderCardStack : RelativeLayout
{
    private var mIndex = 0
    private var mNumVisible = 4
    internal var viewCollection = ArrayList<View>()

    constructor(context: Context?) : super(context)
    {
        //get attrs , assign minVisiableNum
        for (i in 0 until mNumVisible) {
            addContainerViews()
        }
        setupAnimation(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    {
        //get attrs , assign minVisiableNum
        for (i in 0 until mNumVisible) {
            addContainerViews()
        }
        setupAnimation(context)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    // this function creates a collection of 4 FrameLayout that will be shown as a stack
    private fun addContainerViews() {
        val v = FrameLayout(context)
        viewCollection.add(v)
        addView(v)
    }

    private fun setupAnimation(context: Context?) {}
}