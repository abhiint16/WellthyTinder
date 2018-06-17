package abhishekint.com.wellthytinder.app.common

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by abhishek on 17-06-2018.
 */

//Creating CustomViewPager to provide a ViewPager that doesn't change view on swipe

class CustomViewPager: ViewPager
{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}