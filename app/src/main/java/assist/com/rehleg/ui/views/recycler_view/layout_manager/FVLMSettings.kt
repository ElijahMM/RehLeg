package assist.com.rehleg.ui.views.recycler_view.layout_manager

import android.content.Context

/**
 * Created by Sergiu on 31.10.2017.
 */
class FVLMSettings(builder: Builder) {

    var viewWidthDp: Float
    var viewHeightDp: Float
    var viewWidthPx: Int
    var viewHeightPx: Int

    init {
        viewWidthDp = builder.mViewWidthDp
        viewHeightDp = builder.mViewHeightDp
        viewWidthPx = builder.mViewWidthPx
        viewHeightPx = builder.mViewHeightPx
    }


    class Builder(private val mContext: Context) {
        var mViewWidthDp: Float = 0.toFloat()
        var mViewHeightDp: Float = 0.toFloat()
        var mViewWidthPx: Int = 0
        var mViewHeightPx: Int = 0

        /**
         * Sets the `mViewWidthDp` and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param viewWidthDp the `mViewWidthDp` to set
         * @return a reference to this Builder
         */
        fun withViewWidthDp(viewWidthDp: Float): Builder {
            mViewWidthDp = viewWidthDp
            mViewWidthPx = Math.round(mContext.getResources().getDisplayMetrics().density * viewWidthDp)
            mViewWidthPx = Math.min(mContext.getResources().getDisplayMetrics().widthPixels, mViewWidthPx)
            return this
        }

        /**
         * Sets the `mViewHeightDp` and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param viewHeightDp the `mViewHeightDp` to set
         * @return a reference to this Builder
         */
        fun withViewHeightDp(viewHeightDp: Float): Builder {
            mViewHeightDp = viewHeightDp
            mViewHeightPx = Math.round(mContext.resources.displayMetrics.density * viewHeightDp)
            mViewHeightPx = Math.min(mContext.resources.displayMetrics.heightPixels, mViewHeightPx)
            return this
        }

        fun withViewWidthPx(viewWidthPx: Int): Builder {
            mViewWidthPx = viewWidthPx
            mViewWidthDp = viewWidthPx / mContext.resources.displayMetrics.density
            return this
        }

        fun withViewHeightPx(viewHeightPx: Int): Builder {
            mViewHeightPx = viewHeightPx
            mViewHeightDp = viewHeightPx / mContext.resources.displayMetrics.density
            return this
        }

        /**
         * Returns a `FVLMSettings` built from the parameters previously set.
         *
         * @return a `FVLMSettings` built with parameters of this `FVLMSettings.Builder`
         */
        fun build(): FVLMSettings {
            if (java.lang.Float.compare(mViewWidthDp, 0f) == 0) {
                withViewWidthDp(DEFAULT_VIEW_WIDTH_DP)
            }
            if (java.lang.Float.compare(mViewHeightDp, 0f) == 0) {
                withViewHeightDp(DEFAULT_VIEW_HEIGHT_DP)
            }
            return FVLMSettings(this)
        }
    }

    companion object {

        public val DEFAULT_VIEW_WIDTH_DP = 120f
        public val DEFAULT_VIEW_HEIGHT_DP = 160f

        fun newBuilder(context: Context): Builder {
            return Builder(context)
        }
    }
}