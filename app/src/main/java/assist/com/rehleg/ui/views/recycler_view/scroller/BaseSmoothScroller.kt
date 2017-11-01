package assist.com.rehleg.ui.views.recycler_view.scroller

import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller



/**
 * Created by Sergiu on 31.10.2017.
 */
internal abstract class BaseSmoothScroller(context: Context) : LinearSmoothScroller(context) {

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        val layoutManager = layoutManager
        // calculate vector for position
        if (layoutManager != null && layoutManager is LinearLayoutManager) {
            if (childCount == 0) {
                return null
            }
            val firstChildPos = layoutManager.getPosition(layoutManager.getChildAt(0))
            val direction = if (targetPosition < firstChildPos) -1 else 1
            return PointF(direction.toFloat(), 0f)
        }
        return PointF()
    }
}