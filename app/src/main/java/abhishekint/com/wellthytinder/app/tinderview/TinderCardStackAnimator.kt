package abhishekint.com.wellthytinder.app.tinderview

import abhishekint.com.wellthytinder.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by abhishek on 17-06-2018.
 */
class TinderCardStackAnimator(var mCardCollection: ArrayList<View>, val context: Context?) {

    private lateinit var mLayoutsMap: HashMap<View, RelativeLayout.LayoutParams>//used to store the list of card's view
    private lateinit var baseLayout: RelativeLayout.LayoutParams
    private var mStackMargin = 21              //set the margin of cardstack
    private val mRemoteLayouts = arrayOfNulls<RelativeLayout.LayoutParams>(4)   //creating the four list of stack
    private var mRotation: Float = 0.toFloat() //dislike rotation value

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
        initLayout()                      //calling this method to arrange all the card based on StackView
        for (v in mCardCollection) {
            val params = v.layoutParams as RelativeLayout.LayoutParams
            val paramsCopy = RelativeLayout.LayoutParams(params)
            mLayoutsMap!!.put(v, paramsCopy)
        }
        setupRemotes()
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////This func sets margin to the cards and adds some basic shadow
    fun initLayout() {
        val size = mCardCollection.size
        for (v in mCardCollection) {
            var index = mCardCollection.indexOf(v)
            if (index != 0) {
                index -= 1
            }
            val params = RelativeLayout.LayoutParams(baseLayout)
            v.layoutParams = params

            //calling scale method of CardUtils class to set the margin and than put layout into v
            TinderCardUtils.scale(v, -(size - index - 1) * 8)
            TinderCardUtils.move(v, index * mStackMargin, 0)
            v.rotation = 0f                     //setting the rotation of card as 0

            //here calling 3 different shadow layout for each Card
            //which will give shadow effect dynamically ...
            if (index == 0)
            // setting 0 used to add different layout shadow...
                v.background = context!!.resources.getDrawable(R.drawable.card_stack_bg_shadow3)
            else if (index == 1)
                v.background = context!!.resources.getDrawable(R.drawable.card_stack_bg_shadow2)
            else if (index == 2)
                v.background = context!!.resources.getDrawable(R.drawable.card_stack_bg_shadow)
        }
    }

    ////////setting the each card margins and putting view into array of mRemoteLayouts
    private fun setupRemotes() {
        val topView = topView
        mRemoteLayouts[0] = TinderCardUtils.getMoveParams(topView, REMOTE_DISTANCE, -REMOTE_DISTANCE)
        mRemoteLayouts[1] = TinderCardUtils.getMoveParams(topView, REMOTE_DISTANCE, REMOTE_DISTANCE)
        mRemoteLayouts[2] = TinderCardUtils.getMoveParams(topView, -REMOTE_DISTANCE, -REMOTE_DISTANCE)
        mRemoteLayouts[3] = TinderCardUtils.getMoveParams(topView, -REMOTE_DISTANCE, REMOTE_DISTANCE)
    }

    companion object {
        private val REMOTE_DISTANCE = 1000
    }

    //dragging method...it gives the position of object and does the secondary animation of CardStack
    fun drag(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
             distanceY: Float) {

        val topView = topView
        val rotation_coefficient = 20f//
        val layoutParams = topView.layoutParams as RelativeLayout.LayoutParams
        val topViewLayouts = mLayoutsMap[topView]
        val x_diff = (e2.rawX - e1.rawX).toInt()
        val y_diff = (e2.rawY - e1.rawY).toInt()
        layoutParams.leftMargin = topViewLayouts!!.leftMargin + x_diff
        layoutParams.rightMargin = topViewLayouts.rightMargin - x_diff
        layoutParams.topMargin = topViewLayouts.topMargin + y_diff
        layoutParams.bottomMargin = topViewLayouts.bottomMargin - y_diff

        mRotation = x_diff / rotation_coefficient
        topView.rotation = mRotation
        topView.layoutParams = layoutParams

        //animate secondary views.
        for (v in mCardCollection) {
            val index = mCardCollection.indexOf(v)
            if (v != topView && index != 0) {
                val l = TinderCardUtils.scaleFrom(v, mLayoutsMap[v], (Math.abs(x_diff) * 0.05).toInt())
                TinderCardUtils.moveFrom(v, l, 0, (Math.abs(x_diff) * 0.1).toInt())
            }
        }
    }

    //using to set the discard animation of CardStack into ArrayList<Animator>
    fun discard(direction: Int, al: Animator.AnimatorListener) {
        val `as` = AnimatorSet()
        val aCollection = ArrayList<Animator>()
        val topView = topView
        val topParams = topView.layoutParams as RelativeLayout.LayoutParams
        val layout = RelativeLayout.LayoutParams(topParams)
        val discardAnim = ValueAnimator.ofObject(RelativeLayoutParamsEvaluator(), layout, mRemoteLayouts[direction])

        discardAnim.addUpdateListener { value -> topView.layoutParams = value.animatedValue as RelativeLayout.LayoutParams }

        discardAnim.duration = 100//setting the discard removed animation time in ms.
        aCollection.add(discardAnim)//putting the discardAnimation to animation ArrayLIst

        for (i in mCardCollection.indices) {
            val v = mCardCollection[i]

            if (v === topView) continue
            val nv = mCardCollection[i + 1]
            val layoutParams = v.layoutParams as RelativeLayout.LayoutParams
            val endLayout = RelativeLayout.LayoutParams(layoutParams)
            val layoutAnim = ValueAnimator.ofObject(RelativeLayoutParamsEvaluator(), endLayout, mLayoutsMap!![nv])
            layoutAnim.duration = 100
            layoutAnim.addUpdateListener { value -> v.layoutParams = value.animatedValue as RelativeLayout.LayoutParams }
            aCollection.add(layoutAnim)
        }

        `as`.addListener(object : AnimatorListenerAdapter() {


            override fun onAnimationEnd(animation: Animator) {
                reorder()
                al.onAnimationEnd(animation)
                mLayoutsMap = HashMap()
                for (v in mCardCollection) {
                    val params = v.layoutParams as RelativeLayout.LayoutParams
                    val paramsCopy = RelativeLayout.LayoutParams(params)
                    mLayoutsMap!!.put(v, paramsCopy)
                }
            }
        })

        `as`.playTogether(aCollection)
        `as`.start()
    }

    //even card is dragged and not discarded by the user then again this card will come on the top of the stack..
    //this method will give the animation of reverse along with the reverse animation duration
    // time in ms... which is I defined as 250

    fun reverse(e1: MotionEvent, e2: MotionEvent) {
        val topView = topView
        val rotationAnim = ValueAnimator.ofFloat(mRotation, 0f)
        rotationAnim.duration = 250
        rotationAnim.addUpdateListener { v -> topView.rotation = v.animatedValue as Float }

        rotationAnim.start()//start the animation

        for (v in mCardCollection) {
            val layoutParams = v.layoutParams as RelativeLayout.LayoutParams
            val endLayout = RelativeLayout.LayoutParams(layoutParams)
            val layoutAnim = ValueAnimator.ofObject(RelativeLayoutParamsEvaluator(), endLayout, mLayoutsMap!![v])
            layoutAnim.duration = 250
            layoutAnim.addUpdateListener { value -> v.layoutParams = value.animatedValue as RelativeLayout.LayoutParams }
            layoutAnim.start()
        }

    }

    //rearrange the mCardCollection list...
    //
    private fun reorder() {
        val temp = topView
        moveToBack(temp)
        for (i in mCardCollection.size - 1 downTo 1) {

            val current = mCardCollection[i - 1]
            //current replace next
            mCardCollection[i] = current
        }
        mCardCollection[0] = temp

        // temp = getTopView();
    }

    private fun moveToBack(child: View) {
        val parent = child.parent as ViewGroup
        if (null != parent) {
            parent.removeView(child)
            parent.addView(child, 0)
        }
    }
}