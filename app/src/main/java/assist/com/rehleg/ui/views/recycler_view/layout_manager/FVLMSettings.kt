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
    var viewPivotX: Float
    var viewPivotY: Float
    var viewTranslationXStart: Int
    var viewTranslationYStart: Int

    init {
        viewWidthDp = builder.mViewWidthDp
        viewHeightDp = builder.mViewHeightDp
        viewWidthPx = builder.mViewWidthPx
        viewHeightPx = builder.mViewHeightPx
        viewPivotX = builder.mViewPivotX
        viewPivotY = builder.mViewPivotY
        viewTranslationXStart = builder.mViewTranslationXStart
        viewTranslationYStart = builder.mViewTranslationYStart
    }


    class Builder(private val mContext: Context) {
        var mViewWidthDp: Float = 0f
        var mViewHeightDp: Float = 0f
        var mViewWidthPx: Int = 0
        var mViewHeightPx: Int = 0
        var mViewPivotX: Float = 0f
        var mViewPivotY: Float = 0f
        var mViewTranslationXStart: Int = 0
        var mViewTranslationYStart: Int = 0
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

        fun withViewPivotX(viewPivotX: Float): Builder {
            mViewPivotX = viewPivotX
            return this
        }

        fun withViewPivotY(viewPivotY: Float): Builder {
            mViewPivotY = viewPivotY
            return this
        }

        fun withViewTranslationXStart(translationX: Int): Builder {
            mViewTranslationXStart = translationX
            return this
        }

        fun withViewTranslationYStart(translationY: Int): Builder {
            mViewTranslationYStart = translationY
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

            if (java.lang.Float.compare(mViewPivotX, 0f) == 0) {
                mViewPivotX = PIVOT_NOT_SET
            }

            if (java.lang.Float.compare(mViewPivotY, 0f) == 0) {
                mViewPivotY = PIVOT_NOT_SET
            }

            return FVLMSettings(this)
        }
    }

    companion object {

        val DEFAULT_VIEW_WIDTH_DP = 120f
        val DEFAULT_VIEW_HEIGHT_DP = 160f
        val PIVOT_NOT_SET = -1f

        fun newBuilder(context: Context): Builder {
            return Builder(context)
        }
    }
}