package abhishekint.com.wellthytinder.app.tinderview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import java.util.ArrayList

/**
 * Created by abhishek on 17-06-2018.
 */
class TinderCardStack : RelativeLayout {
    private var mIndex = 0
    private var mNumVisible = 4
    internal var viewCollection = ArrayList<View>()
    private lateinit var mCardAnimator: TinderCardStackAnimator
    private var mEventListener: TinderCardEventListener = DefaultStackEventListener(10)

    constructor(context: Context?) : super(context) {
        //get attrs , assign minVisiableNum
        for (i in 0 until mNumVisible) {
            addContainerViews()
        }
        setupAnimation(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
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

    private fun setupAnimation(context: Context?) {
        val cardView = viewCollection[viewCollection.size - 1]
        mCardAnimator = TinderCardStackAnimator(viewCollection, context) //creating an object of cardAnimator and passing collection of FrameLayout
        mCardAnimator!!.initLayout() //initialize the cardAnimator using object

        val dd = DragGestureDetactor(this.context, object : DragGestureDetactor.DragListener {
            override fun onDragStart(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                mCardAnimator.drag(e1, e2, distanceX, distanceY)
                mCardAnimator.drag(e1, e2, distanceX, distanceY)
                return true
            }

            override fun onDragContinue(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                val x1 = e1.rawX
                val y1 = e1.rawY
                val x2 = e2.rawX
                val y2 = e2.rawY
                //float distance = CardUtils.distance(x1,y1,x2,y2);
                val direction = TinderCardUtils.direction(x1, y1, x2, y2)
                mCardAnimator.drag(e1, e2, distanceX, distanceY)
                mEventListener.swipeContinue(direction, Math.abs(x2 - x1), Math.abs(y2 - y1))
                return true
            }

            override fun onDragEnd(e1: MotionEvent?, e2: MotionEvent): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTapUp(): Boolean {
                mEventListener.topCardTapped()
                return true
            }

        })
    }

    interface TinderCardEventListener {
        //section
        // 0 | 1
        //--------
        // 2 | 3
        // swipe distance, most likely be used with height and width of a view ;

        fun swipeEnd(section: Int, distance: Float): Boolean

        fun swipeStart(section: Int, distance: Float): Boolean

        fun swipeContinue(section: Int, distanceX: Float, distanceY: Float): Boolean

        fun discarded(mIndex: Int, direction: Int)

        fun topCardTapped()
    }
}