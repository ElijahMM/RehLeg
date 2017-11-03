package assist.com.rehleg.ui.utils


import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE
import android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK
import android.text.BoringLayout
import android.util.DisplayMetrics
import android.view.WindowManager
import assist.com.rehleg.ui.fragments.OtherVideosFragment


/**
 * Created by Sergiu on 30.10.2017.
 */
class Utils {
    companion object {
        fun dpToPx(context: Context, dp: Float): Float {
            val displayMetrics = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
        }

        fun pxToDp(context: Context, px: Float): Float {
            val displayMetrics = context.resources.displayMetrics
            return px / displayMetrics.density
        }

        fun isTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }

        fun getActionBarHeight(context: Context): Int {
            val tv = TypedValue()
            var actionBarHeight = 0
            if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
            }
            return actionBarHeight
        }

        fun getDisplyMetrics(context: Context) :DisplayMetrics{
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(metrics)
            return metrics
        }
    }
}