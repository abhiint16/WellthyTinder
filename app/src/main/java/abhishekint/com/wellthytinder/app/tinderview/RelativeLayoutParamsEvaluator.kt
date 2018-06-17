package abhishekint.com.wellthytinder.app.tinderview

import android.animation.TypeEvaluator
import android.widget.RelativeLayout

/**
 * Created by abhishek on 18-06-2018.
 */
class RelativeLayoutParamsEvaluator : TypeEvaluator<RelativeLayout.LayoutParams> {


    override fun evaluate(fraction: Float, start: RelativeLayout.LayoutParams,
                          end: RelativeLayout.LayoutParams): RelativeLayout.LayoutParams {

        val result = RelativeLayout.LayoutParams(start)
        result.leftMargin += ((end.leftMargin - start.leftMargin) * fraction).toInt()
        result.rightMargin += ((end.rightMargin - start.rightMargin) * fraction).toInt()
        result.topMargin += ((end.topMargin - start.topMargin) * fraction).toInt()
        result.bottomMargin += ((end.bottomMargin - start.bottomMargin) * fraction).toInt()
        return result
    }

}
