package assist.com.rehleg.ui.views.recycler_view.layout_manager

import android.content.Context
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.ui.views.recycler_view.animation.AnimationHelper
import assist.com.rehleg.ui.views.recycler_view.scroller.FeaturedVideosCenterScroller
import assist.com.rehleg.ui.views.recycler_view.scroller.FeaturedVideosScroller


/**
 * Created by Sergiu on 31.10.2017.
 */
class FeaturedVideosLayoutManager(@NonNull context: Context, settings: FVLMSettings) : RecyclerView.LayoutManager() {
    /**
     * Settings for fan layout manager. [FVLMSettings.Builder]
     */
    private var mSettings: FVLMSettings = settings
    /**
     * Map with view cache.
     */
    private val mViewCache = SparseArray<View>()
    /**
     * LinearSmoothScroller for switch views.
     */
    private val mScroller: FeaturedVideosScroller
    /**
     * LinearSmoothScroller to show view in the middle of the screen.
     */
    private val mCenterScroller: FeaturedVideosCenterScroller

    /**
     * Helper module need to implement 'open','close', 'shift' views functionality.
     */
    @NonNull
    private var mAnimationHelper: AnimationHelper? = null
    /**
     * Position of selected item in adapter. ADAPTER!!
     */
    /**
     * @return selected item position
     */
    var selectedItemPosition = RecyclerView.NO_POSITION
        private set
    /**
     * Position of item we need to scroll to right now.
     */
    private var mScrollToPosition = RecyclerView.NO_POSITION
    /**
     * Need to block some events between smooth scroll and select item animation.
     * true before start smoothScroll to selected item
     * false after smooth scroll finished and after select animation is started.
     */
    private var mIsWaitingToSelectAnimation = false
    /**
     * Need to block some events while scaling view.
     * true right after smooth scroll finished scrolling.
     */
    private var mIsSelectAnimationInProcess = false

    /**
     * Need to block some events while deselecting item is preparing.
     */
    private var mIsWaitingToDeselectAnimation = false
    /**
     * Need to block some events.
     */
    private var mIsDeselectAnimationInProcess = false

    /**
     * Saved state for layout manager.
     */
    private var mPendingSavedState: SavedState? = null
    /**
     * true if item selected
     */
    private var mIsSelected = false

    /**
     * View in center of screen
     */
    private var mCenterView: View? = null

    val isItemSelected: Boolean
        get() = selectedItemPosition != RecyclerView.NO_POSITION

    init {
        // create default settings
        // create default animation helper
        mAnimationHelper = AnimationHelper()
        // create default FanCardScroller
        mScroller = FeaturedVideosScroller(context)
        // set callback which return calculated scroll time
        mScroller.videoTimeCallback = object : FeaturedVideosScroller.VideoTimeCallback {
            override fun onTimeForScrollingCalculated(targetPosition: Int, time: Int) {
                selectItem(targetPosition, time)
            }
        }

        // create default smooth scroller to show item in the middle of the screen
        mCenterScroller = FeaturedVideosCenterScroller(context)
    }

    fun saveState() {
        mPendingSavedState = SavedState(findCurrentCenterViewPos(), mIsSelected)
    }

    override fun onSaveInstanceState(): Parcelable? {
        saveState()
        return mPendingSavedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state != null && state is FeaturedVideosLayoutManager.SavedState) {
            mPendingSavedState = state
            // center view position
            mScrollToPosition = mPendingSavedState!!.mCenterItemPosition
            // position for selected item
            selectedItemPosition = if (mPendingSavedState!!.isSelected) mScrollToPosition else RecyclerView.NO_POSITION
            // selected state
            mIsSelected = mPendingSavedState!!.isSelected
        }
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        // find center view before detach or recycle all views
        if (mSettings.viewHeightDp == FVLMSettings.DEFAULT_VIEW_HEIGHT_DP) {
            mSettings.viewHeightPx = height
        }

        mCenterView = findCurrentCenterView()
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler)
            return
        }
        detachAndScrapAttachedViews(recycler)
        fill(recycler)
    }

    /**
     * Method create or reuse views for recyclerView.
     *
     * @param recycler recycler from the recyclerView
     */
    private fun fill(recycler: RecyclerView.Recycler?) {
        mViewCache.clear()

        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val pos = getPosition(view)
            mViewCache.put(pos, view)
        }

        for (i in 0 until mViewCache.size()) {
            detachView(mViewCache.valueAt(i))
        }

        // position for center view
        val centerViewPosition = if (mCenterView == null) 0 else getPosition(mCenterView!!)

        // left offset for center view
        val centerViewOffset = if (mCenterView == null)
            (width / 2f - mSettings.viewHeightPx / 2f).toInt()
        else
            getDecoratedLeft(mCenterView!!)

        // main fill logic
        if (mScrollToPosition != RecyclerView.NO_POSITION) {
            // fill views if start position not in the middle of screen (restore state)
            fillRightFromCenter(mScrollToPosition, centerViewOffset, recycler)
        } else {
            // fill views if start position in the middle of the screen
            fillRightFromCenter(centerViewPosition, centerViewOffset, recycler)
        }

        //update center view after recycle all views
        if (childCount != 0) {
            mCenterView = findCurrentCenterView()
        }

        for (i in 0 until mViewCache.size()) {
            recycler!!.recycleView(mViewCache.valueAt(i))
        }
        // update rotations.
        updateArcViewPositions()
    }

    /**
     * Measure view with margins and specs
     *
     * @param child      view to measure
     * @param widthSpec  spec for width
     * @param heightSpec spec for height
     */
    private fun measureChildWithDecorationsAndMargin(child: View, widthSpec: Int, heightSpec: Int) {
        var widthSpec1 = widthSpec
        var heightSpec1 = heightSpec

        val decorRect = Rect()
        calculateItemDecorationsForChild(child, decorRect)
        val lp = child.layoutParams as RecyclerView.LayoutParams
        widthSpec1 = updateSpecWithExtra(widthSpec1, lp.leftMargin + decorRect.left,
                lp.rightMargin + decorRect.right)
        heightSpec1 = updateSpecWithExtra(heightSpec1, lp.topMargin + decorRect.top,
                lp.bottomMargin + decorRect.bottom)
        child.measure(widthSpec1, heightSpec1)
    }

    private fun updateSpecWithExtra(spec: Int, startInset: Int, endInset: Int): Int {
        if (startInset == 0 && endInset == 0) {
            return spec
        }
        val mode = View.MeasureSpec.getMode(spec)
        return if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.makeMeasureSpec(
                    View.MeasureSpec.getSize(spec) - startInset - endInset, mode)
        } else spec
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        // after fillRightFromCenter(...) we don't need this param.
        mScrollToPosition = RecyclerView.NO_POSITION
        //        // after fillRightFromCenter(...) we don't need this param.
        mPendingSavedState = null

        if (dx == RecyclerView.NO_POSITION) {
            val delta = scrollHorizontallyInternal(dx)
            offsetChildrenHorizontal(-delta)
            fill(recycler)
            return delta
        }

        // if animation in progress block scroll
        if (mIsDeselectAnimationInProcess || mIsSelectAnimationInProcess) {
            return 0
        }
        val delta = scrollHorizontallyInternal(dx)
        offsetChildrenHorizontal(-delta)
        fill(recycler)
        return delta
    }

    override fun onMeasure(recycler: RecyclerView.Recycler?, state: RecyclerView.State?, widthSpec: Int, heightSpec: Int) {
        var heightMeasureSpec = heightSpec

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val scaledHeight = (mSettings.viewHeightPx * mAnimationHelper!!.viewScaleFactor).toLong()
        val scaledWidth = (mSettings.viewWidthPx * mAnimationHelper!!.viewScaleFactor).toLong()
        val height = if (heightMode == View.MeasureSpec.EXACTLY)
            View.MeasureSpec.getSize(heightMeasureSpec)
        else
            Math.sqrt((scaledHeight * scaledHeight + scaledWidth * scaledWidth).toDouble()).toInt()


        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        updateArcViewPositions()
        super.onMeasure(recycler, state, widthSpec, heightMeasureSpec)
    }

    /**
     * Calculate delta x for views.
     *
     * @param dx fling (user scroll gesture) delta x
     * @return delta x for views
     */
    private fun scrollHorizontallyInternal(dx: Int): Int {
        val childCount = childCount
        // check child count
        if (childCount == 0) {
            return 0
        }
        // items count in the adapter
        val itemCount = itemCount

        var leftView = getChildAt(0)
        var rightView = getChildAt(childCount - 1)
        // search left and right views.
        for (i in 0 until getChildCount()) {
            val view = getChildAt(i)
            if (getDecoratedLeft(leftView) > getDecoratedLeft(view)) {
                leftView = view
            }
            if (getDecoratedRight(rightView) < getDecoratedRight(view)) {
                rightView = view
            }
        }
        // area with filling views. need to find borders
        val viewSpan = if (getDecoratedRight(rightView) > width)
            getDecoratedRight(rightView)
        else
            width - if (getDecoratedLeft(leftView) < 0) getDecoratedLeft(leftView) else 0

        // check left and right borders
        if (viewSpan < width) {
            return 0
        }

        var delta = 0
        if (dx < 0) {
            // move views left

            // position for left item in the adapter
            val firstViewAdapterPos = getPosition(leftView)

            if (firstViewAdapterPos > 0) {
                // if item isn't first in the adapter
                delta = dx
            } else {
                // if item first in the adapter

                // stop scrolling if item in the middle.
                val viewLeft = getDecoratedLeft(leftView) - width / 2 + getDecoratedMeasuredWidth(leftView) / 2
                delta = Math.max(viewLeft, dx)
            }
        } else if (dx > 0) {
            // move views right

            // position for right item in the adapter
            val lastViewAdapterPos = getPosition(rightView)

            delta = if (lastViewAdapterPos < itemCount - 1) {
                // if item isn't last in the adapter
                dx
            } else {
                // if item last in the adapter

                // stop scrolling if item in the middle.
                val viewRight = getDecoratedRight(rightView) + width / 2 - getDecoratedMeasuredWidth(rightView) / 2
                val parentRight = width
                Math.min(viewRight - parentRight, dx)
            }
        }
        return delta
    }

    /**
     * Change pivot, rotation, translation for view to create fan effect.
     */
    private fun updateArcViewPositions() {

        // +++++ init params +++++
        val halfWidth = (width / 2).toFloat()
        val sagitta: Double = mSettings.viewTranslationYStart.toDouble() - 14
        val radius = (sagitta * sagitta + halfWidth * halfWidth) / (2 * sagitta)
        val powRadius = radius * radius
        var rotation: Double
        var halfViewWidth: Float
        var deltaX: Double
        var deltaY: Double
        // ----- init params -----

        for (i in 0 until childCount) {
            val view = getChildAt(i)

            halfViewWidth = view.width.toFloat() / 2

            // change pivot point
            view.pivotX = if (mSettings.viewPivotX == FVLMSettings.PIVOT_NOT_SET) halfViewWidth else mSettings.viewPivotX
            view.pivotY = if (mSettings.viewPivotY == FVLMSettings.PIVOT_NOT_SET) 0f else mSettings.viewPivotY

            // distance between center of screen to center of view in x-axis
            deltaX = (halfWidth - getDecoratedLeft(view).toFloat() - halfViewWidth).toDouble()

            // distance in which need to move view in y-axis. Low accuracy
            deltaY = radius - Math.sqrt(powRadius - deltaX * deltaX)
            view.translationY = mSettings.viewTranslationYStart - deltaY.toFloat()
            // calculate view rotation
            rotation = (Math.toDegrees(Math.asin((radius - deltaY) / radius)) - 90) * Math.signum(-deltaX)

            view.rotation = rotation.toFloat()
        }
    }

    /**
     * Method draw view using center view position.
     *
     * @param centerViewPosition position of center view (anchor). This view will be in center
     * @param recycler           Recycler from the recyclerView
     */
    private fun fillRightFromCenter(centerViewPosition: Int, centerViewOffset: Int, recycler: RecyclerView.Recycler?) {
        // left limit. need to prepare with before they will be show to user.
        val leftBorder = 0

        // right limit.
        val rightBorder = width + 2 * mSettings.viewWidthPx
        var leftViewOffset = centerViewOffset
        var leftViewPosition = centerViewPosition

        val overlapDistance: Int = -mSettings.viewWidthPx / 10

        var fillRight = true

        // specs for item views
        val widthSpec = View.MeasureSpec.makeMeasureSpec(mSettings.viewWidthPx, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(mSettings.viewHeightPx, View.MeasureSpec.EXACTLY)

        // if have to restore state with selected item
        val hasPendingStateSelectedItem = mPendingSavedState != null && mPendingSavedState!!.isSelected &&
                mPendingSavedState!!.mCenterItemPosition != RecyclerView.NO_POSITION

        // offset for left and right views in case we have to restore pending state with selected view.
        // this is delta distance between overlap cards state and collapse (selected card) card state
        // need to use ones for all left view and right views
        val deltaOffset = mSettings.viewWidthPx / 2

        // --------- Prepare data ---------

        // search left position for first view
        while (leftViewOffset > leftBorder && leftViewPosition >= 0) {
            leftViewOffset -= mSettings.viewWidthPx + Math.abs(overlapDistance)
            leftViewPosition--
        }

        if (leftViewPosition < 0) {
            // if theoretically position for left view is less than left view.
            leftViewOffset += (mSettings.viewWidthPx + Math.abs(overlapDistance)) * Math.abs(leftViewPosition)
            leftViewPosition = 0
        }

        // offset for left views if we restore state and have selected item
        if (hasPendingStateSelectedItem && leftViewPosition != mPendingSavedState!!.mCenterItemPosition) {
            leftViewOffset += (-deltaOffset)
        }

        var selectedView: View? = null
        while (fillRight && leftViewPosition < itemCount) {

            // offset for current view if we restore state and have selected item
            if (hasPendingStateSelectedItem && leftViewPosition == mPendingSavedState!!.mCenterItemPosition && leftViewPosition != 0) {
                leftViewOffset += deltaOffset
            }

            // get view from local cache
            var view: View? = mViewCache.get(leftViewPosition)

            if (view == null) {
                // get view from recycler
                view = recycler!!.getViewForPosition(leftViewPosition)
                // optimization for view rotation
                view!!.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                // add vew to the recyclerView
                addView(view)
                // measuring view
                measureChildWithDecorationsAndMargin(view, widthSpec, heightSpec)
                // set offsets, with and height in the recyclerView
                layoutDecorated(view, leftViewOffset, 0,
                        leftViewOffset + mSettings.viewWidthPx, mSettings.viewHeightPx)
            } else {
                attachView(view, leftViewPosition)
                mViewCache.remove(leftViewPosition)
            }

            view.scaleX = 1f
            view.scaleY = 1f

            if (mIsSelected && centerViewPosition == leftViewPosition) {
                selectedView = view
            }


            // calculate position for next view. last position + view height - overlap between views.
            leftViewOffset = leftViewOffset + mSettings.viewWidthPx - overlapDistance

            // check right border. stop loop if next view is > then right border.
            fillRight = leftViewOffset < rightBorder

            // offset for right views if we restore state and have selected item
            if (hasPendingStateSelectedItem && leftViewPosition == mPendingSavedState!!.mCenterItemPosition) {
                leftViewOffset += deltaOffset
            }

            leftViewPosition++
        }

        // if we have to restore state with selected item
        // this part need to scale center selected view
        if (hasPendingStateSelectedItem) {
            //            View view = findCurrentCenterView();
            if (selectedView != null) {
                selectedView.scaleX = mAnimationHelper!!.viewScaleFactor
                selectedView.scaleY = mAnimationHelper!!.viewScaleFactor
            }
        }
    }

    /**
     * Method change item state from close to open and open to close (switch state)
     *
     * @param recyclerView         current recycler view. Need to smooth scroll.
     * @param selectedViewPosition item view position
     */
    fun switchItem(@Nullable recyclerView: RecyclerView?, selectedViewPosition: Int) {
        if (mIsDeselectAnimationInProcess || mIsSelectAnimationInProcess ||
                mIsWaitingToDeselectAnimation || mIsWaitingToSelectAnimation) {
            // block event if any animation in progress
            return
        }

        if (recyclerView != null) {
            // if item not selected need to smooth scroll and then select item
            smoothScrollToPosition(recyclerView, null, selectedViewPosition)
        }
    }


    private fun selectItem(position: Int, delay: Int) {
        // save position of view which will be selected
        selectedItemPosition = position
        // save selected stay... no way back...
        mIsSelected = true
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        // when user stop scrolling
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            // show view in the middle of screen
            scrollToCenter()
        }
    }

    /**
     * Scroll views left or right so nearest view will be in the middle of screen
     */
    private fun scrollToCenter() {
        val nearestToCenterView = findCurrentCenterView()
        if (nearestToCenterView != null) {
            // scroll to the nearest view
            mCenterScroller.targetPosition = getPosition(nearestToCenterView)
            startSmoothScroll(mCenterScroller)
        }
    }

    override fun scrollToPosition(position: Int) {
        mScrollToPosition = position
        requestLayout()
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        if (position >= itemCount) {
            // if position is not in range
            return
        }
        // smooth scroll to position
        mScroller.targetPosition = position
        startSmoothScroll(mScroller)
    }

    /**
     * Find view in the middle of screen
     *
     * @return center View
     */
    @Nullable
    private fun findCurrentCenterView(): View? {
        // +++++ prepare data +++++

        // center of the screen in x-axis
        val centerX = width / 2f
        val viewHalfWidth = mSettings.viewWidthPx / 2f
        var nearestToCenterView: View? = null
        var nearestDeltaX = 0
        var item: View
        var centerXView: Int
        // ----- prepare data -----

        // search nearest to center view
        val count = childCount
        var i = 0
        while (i < count) {
            item = getChildAt(i)
            centerXView = (getDecoratedLeft(item) + viewHalfWidth).toInt()
            if (nearestToCenterView == null || Math.abs(nearestDeltaX) > Math.abs(centerX - centerXView)) {
                nearestToCenterView = item
                nearestDeltaX = (centerX - centerXView).toInt()
            }
            i++
        }
        return nearestToCenterView
    }

    /**
     * Find position of view in the middle of screen
     *
     * @return position of center view or [RecyclerView.NO_POSITION]
     */
    private fun findCurrentCenterViewPos(): Int {
        val view = mCenterView
        return if (view == null) RecyclerView.NO_POSITION else getPosition(view)
    }

    override fun onItemsChanged(recyclerView: RecyclerView?) {
        super.onItemsChanged(recyclerView)
        recyclerView!!.stopScroll()
        saveState()
        if (itemCount <= selectedItemPosition) {
            selectedItemPosition = RecyclerView.NO_POSITION
            // save selected state for center view
            mPendingSavedState!!.isSelected = false
            mIsSelected = false
        }
    }

    override fun onItemsAdded(recyclerView: RecyclerView?, positionStart: Int, itemCount: Int) {
        super.onItemsAdded(recyclerView, positionStart, itemCount)
        recyclerView!!.stopScroll()
        saveState()
    }

    override fun onItemsUpdated(recyclerView: RecyclerView?, positionStart: Int, itemCount: Int) {
        super.onItemsUpdated(recyclerView, positionStart, itemCount)
        recyclerView!!.stopScroll()
        saveState()
    }

    override fun onItemsUpdated(recyclerView: RecyclerView, positionStart: Int, itemCount: Int, payload: Any?) {
        super.onItemsUpdated(recyclerView, positionStart, itemCount, payload)
        recyclerView.stopScroll()
        saveState()
    }

    override fun onItemsRemoved(recyclerView: RecyclerView?, positionStart: Int, itemCount: Int) {
        super.onItemsRemoved(recyclerView, positionStart, itemCount)
        recyclerView!!.stopScroll()
        saveState()
        if (selectedItemPosition >= positionStart && selectedItemPosition < positionStart + itemCount) {
            selectedItemPosition = RecyclerView.NO_POSITION
            // save selected state for center view
            mPendingSavedState!!.isSelected = false
        }
    }

    private class SavedState(var mCenterItemPosition: Int = RecyclerView.NO_POSITION,
                             var isCollapsed: Boolean = false,
                             var isSelected: Boolean = false) : Parcelable {

        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readByte() != 0.toByte(),
                parcel.readByte() != 0.toByte())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(mCenterItemPosition)
            parcel.writeByte(if (isCollapsed) 1 else 0)
            parcel.writeByte(if (isSelected) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}