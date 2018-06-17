package abhishekint.com.wellthytinder.app.tinderview

/**
 * Created by abhishek on 18-06-2018.
 */
class DefaultStackEventListener : TinderCardStack.TinderCardEventListener {
    private var mThreshold = 0

    constructor(mThreshold: Int) {
        this.mThreshold = mThreshold
    }


    override fun swipeEnd(section: Int, distance: Float): Boolean {
        return distance > mThreshold
    }

    override fun swipeStart(section: Int, distance: Float): Boolean {
        return true
    }

    override fun swipeContinue(section: Int, distanceX: Float, distanceY: Float): Boolean {
        return true
    }

    override fun discarded(mIndex: Int, direction: Int) {

    }

    override fun topCardTapped() {

    }

}