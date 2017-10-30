package assist.com.rehleg.ui.utils


import android.content.Context
import android.util.TypedValue


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
    }
}