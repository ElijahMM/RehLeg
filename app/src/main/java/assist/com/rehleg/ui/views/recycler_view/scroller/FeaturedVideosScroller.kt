package assist.com.rehleg.ui.views.recycler_view.scroller

import android.content.Context
import android.support.annotation.Nullable
import android.support.v7.widget.LinearSmoothScroller
import android.util.DisplayMetrics
import android.view.View


/**
 * Created by Sergiu on 31.10.2017.
 */
class FeaturedVideosScroller(context: Context) : BaseSmoothScroller(context) {
    companion object {
        private val MILLISECONDS = 100f
    }

    @Nullable
    var videoTimeCallback: VideoTimeCallback? = null

    override fun getHorizontalSnapPreference(): Int {
        return LinearSmoothScroller.SNAP_TO_START
    }

    override fun calculateDxToMakeVisible(view: View, snapPreference: Int): Int {
        val layoutManager = layoutManager

        return if (layoutManager != null) {
            super.calculateDxToMakeVisible(view, snapPreference) + layoutManager.width / 2 - view.width / 2
        } else {
            super.calculateDxToMakeVisible(view, snapPreference)
        }
    }

    override fun calculateTimeForScrolling(dx: Int): Int {
        val time = super.calculateTimeForScrolling(dx)
        if (videoTimeCallback != null) {
            videoTimeCallback!!.onTimeForScrollingCalculated(targetPosition, time)
        }

        return time
    }

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return MILLISECONDS / displayMetrics.densityDpi
    }

    interface VideoTimeCallback {
        fun onTimeForScrollingCalculated(targetPosition: Int, time: Int)
    }
}