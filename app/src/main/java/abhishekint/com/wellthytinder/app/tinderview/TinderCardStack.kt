package abhishekint.com.wellthytinder.app.tinderview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
    private lateinit var mOnTouchListener: View.OnTouchListener
    private lateinit var mAdapter: ArrayAdapter<*>
    private var mContentResource = 0

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

    private val mOb = object : DataSetObserver() {
        override fun onChanged() {
            reset(false)
        }
    }

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
                //reverse(e1,e2);
                val x1 = e1!!.rawX
                val y1 = e1.rawY
                val x2 = e2.rawX
                val y2 = e2.rawY
                val distance = TinderCardUtils.distance(x1, y1, x2, y2)
                val direction = TinderCardUtils.direction(x1, y1, x2, y2)
                val discard = mEventListener.swipeEnd(direction, distance)
                if (discard) {
                    mCardAnimator.discard(direction, object : AnimatorListenerAdapter() {

                        override fun onAnimationEnd(arg0: Animator) {
                            mCardAnimator.initLayout()
                            mIndex++
                            mEventListener.discarded(mIndex, direction)

                            //mIndex = mIndex%mAdapter.getCount();
                            loadLast()
                            viewCollection[0].setOnTouchListener(null)
                            viewCollection[viewCollection.size - 1]
                                    .setOnTouchListener(mOnTouchListener)
                        }
                    })
                } else {
                    mCardAnimator.reverse(e1, e2)
                }
                return true
            }

            override fun onTapUp(): Boolean {
                mEventListener.topCardTapped()
                return true
            }

        })
    }

    private fun loadLast() {
        val parent = viewCollection[0] as ViewGroup

        val lastIndex = mNumVisible - 1 + mIndex
        if (lastIndex > mAdapter.getCount() - 1) {
            parent.visibility = View.GONE //hiding the top view and returning
            return
        }
        val child = mAdapter.getView(lastIndex, getContentView(), parent)
        parent.removeAllViews()// remove all previous view
        parent.addView(child) // and then adding new
    }

    //returning a new view even
    private fun getContentView(): View? {
        var contentView: View? = null
        if (mContentResource != 0) {
            val lf = LayoutInflater.from(context)
            contentView = lf.inflate(mContentResource, null)
        }
        return contentView
    }

    //when need to reset the card into stack
    fun reset(resetIndex: Boolean) {
        if (resetIndex) mIndex = 0
        removeAllViews()
        viewCollection.clear()
        for (i in 0 until mNumVisible) {
            addContainerViews()
        }
        setupAnimation(context)
        loadData()
    }


    //call to load data based on index
    //this method will put Gone or visible property to view...
    private fun loadData() {
        for (i in mNumVisible - 1 downTo 0) {
            val parent = viewCollection[i] as ViewGroup
            val index = mIndex + mNumVisible - 1 - i
            if (index > mAdapter?.getCount()!! - 1) {
                parent.visibility = View.GONE
            } else {
                val child = mAdapter?.getView(index, getContentView(), this)
                parent.addView(child)
                parent.visibility = View.VISIBLE
            }
        }
    }

    //used to set Adapter
    fun setAdapter(adapter: ArrayAdapter<*>) {
        mAdapter = adapter
        adapter.registerDataSetObserver(mOb)
        if (mAdapter != null) {
            mAdapter!!.unregisterDataSetObserver(mOb)
        }
        loadData()
    }

    fun setContentResource(res: Int) {
        mContentResource = res
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