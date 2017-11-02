package assist.com.rehleg.ui.animators

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import android.os.Build
import android.view.View
import assist.com.rehleg.R
import android.annotation.TargetApi


/**
 * Created by mihai on 02.11.2017.
 */
class ParallaxScrollView : ScrollView {

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context) : super(context)

    private val DEFAULT_SCROLL_FACTOR = 0.6f
    private var mScrollFactor = DEFAULT_SCROLL_FACTOR

    private val PRE_HONEYCOMB = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
    private var mBackgroundResId: Int = 0
    private var mBackgroundView: View? = null


    /**
     * Extract the additional attributes needed.
     *
     * @param context  The context used to create the view.
     * @param attrs    The desired attributes to be retrieved.
     * @param defStyle A resource identifier of a style resource that supplies default values for
     * the TypedArray. Can be 0 if defaults should not be used.
     */
    private fun initView(context: Context, attrs: AttributeSet?, defStyle: Int) {
        if (isInEditMode) {
            return
        }

        if (attrs != null) {
            val values = context.obtainStyledAttributes(attrs, R.styleable.ParallaxScrollView, defStyle, 0)
            mBackgroundResId = values.getResourceId(R.styleable.ParallaxScrollView_backgroundView, 0)
            mScrollFactor = values.getFloat(R.styleable.ParallaxScrollView_scrollFactor, DEFAULT_SCROLL_FACTOR)
            values.recycle()
        }

        // Disable fading edge
        isVerticalFadingEdgeEnabled = false
    }

    /**
     * Define which view that will be subject to parallax scrolling.
     *
     * @param resId The identifier of the resource that will be parallax scrolling.
     */
    fun setBackgroundView(resId: Int) {
        mBackgroundView = findViewById(resId)
    }

    /**
     * Define which view that will be subject to parallax scrolling.
     *
     * @param view The view that will be parallax scrolling.
     */
    fun setBackgroundView(view: View) {
        mBackgroundView = view
    }

    /**
     * Define the pace with witch the background view scrolls in relation to the scrolling
     * of the [android.widget.ScrollView].
     *
     * @param scrollFactor A factor defining the scroll pace of the background view.
     */
    fun setScrollFactor(scrollFactor: Float) {
        mScrollFactor = scrollFactor
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (changed) {
            // On layout changes (eg. orientation change) scroll offset might have changed.
            // Setting a new Y translation here removes any background view hiccups.
            translateBackgroundView(scrollY)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // If resource was set in XML, that child view will be available upon attaching this view
        // to the view hierarchy.
        if (mBackgroundResId > 0 && mBackgroundView == null) {
            mBackgroundView = findViewById(mBackgroundResId)
            mBackgroundResId = 0
        }
    }

    override fun onDetachedFromWindow() {
        // Clean up for versions prior to Honeycomb. Since translation is achieved using the
        // Android animation API all animations are removed when this view is detached.
        if (PRE_HONEYCOMB && mBackgroundView != null) {
            mBackgroundView!!.clearAnimation()
        }
        mBackgroundView = null
        super.onDetachedFromWindow()
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        translateBackgroundView(t)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun translateBackgroundView(y: Int) {
        if (mBackgroundView != null) {
            val translationY = (y * mScrollFactor) as Int
            if (PRE_HONEYCOMB) {
                ViewCompat.wrap(mBackgroundView!!).setTranslationY(translationY)
            } else {
                mBackgroundView!!.translationY = translationY.toFloat()
            }
        }
    }

}