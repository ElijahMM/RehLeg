package assist.com.rehleg.ui.views.recycler_view.scroller

import android.content.Context
import android.support.v7.widget.LinearSmoothScroller
import android.util.DisplayMetrics
import android.view.View


/**
 * Created by Sergiu on 31.10.2017.
 */
internal class FeaturedVideosCenterScroller(context: Context) : BaseSmoothScroller(context) {
    private val MILLISECONDS = 300f

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

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return MILLISECONDS / displayMetrics.densityDpi
    }
}