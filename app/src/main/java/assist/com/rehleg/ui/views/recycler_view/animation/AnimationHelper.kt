package assist.com.rehleg.ui.views.recycler_view.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.DecelerateInterpolator


/**
 * Created by Sergiu on 31.10.2017.
 */
internal class AnimationHelper {

    // scale factor for view in open/close animations
    private val ANIMATION_VIEW_SCALE_FACTOR = 1.5f

    // base duration for open animation
    private val ANIMATION_SINGLE_OPEN_DURATION = 300

    // base duration for close animation
    private val ANIMATION_SINGLE_CLOSE_DURATION = 300

    // base duration for shift animation
    private val ANIMATION_SHIFT_VIEWS_DURATION = 200

    // base threshold duration for shift animation
    private val ANIMATION_SHIFT_VIEWS_DELAY_THRESHOLD = 50

    // base threshold duration for open/close animation (bounce effect)
    private val ANIMATION_VIEW_SCALE_FACTOR_THRESHOLD = 0.4f

    val viewScaleFactor: Float
        get() = ANIMATION_VIEW_SCALE_FACTOR

    fun openItem(@NonNull view: View, delay: Int, animatorListener: Animator.AnimatorListener) {
        // create animator
        val valueAnimator = ValueAnimator.ofFloat(1f, ANIMATION_VIEW_SCALE_FACTOR + ANIMATION_VIEW_SCALE_FACTOR_THRESHOLD)
        valueAnimator.addUpdateListener { valueAnim ->
            // get update value
            var value = valueAnim.animatedValue as Float

            if (value < 1f + ANIMATION_VIEW_SCALE_FACTOR_THRESHOLD / 2) {
                // make view less
                value = Math.abs(value - 2f)
            } else {
                // make view bigger
                value -= ANIMATION_VIEW_SCALE_FACTOR_THRESHOLD
            }

            scaleView(view, value)
        }

        valueAnimator.startDelay = delay.toLong()
        valueAnimator.duration = ANIMATION_SINGLE_OPEN_DURATION.toLong()
        valueAnimator.addListener(animatorListener)
        valueAnimator.start()

    }

    fun closeItem(@NonNull view: View, delay: Int, animatorListener: Animator.AnimatorListener) {
        val valueAnimator = ValueAnimator.ofFloat(ANIMATION_VIEW_SCALE_FACTOR, 1f)
        valueAnimator.addUpdateListener { valueAnim ->
            // get update value
            val value = valueAnim.animatedValue as Float
            scaleView(view, value)
        }
        valueAnimator.startDelay = delay.toLong()
        valueAnimator.duration = ANIMATION_SINGLE_CLOSE_DURATION.toLong()
        valueAnimator.addListener(animatorListener)
        valueAnimator.start()
    }

    private fun scaleView(view: View, value: Float) {

        // change pivot point to the bottom middle
        view.pivotX = view.width.toFloat() / 2
        view.pivotY = view.height.toFloat()

        // scale view
        view.scaleX = value
        view.scaleY = value
    }

    fun shiftSideViews(@NonNull views: Collection<ViewAnimationInfo>,
                       delay: Int,
                       @NonNull layoutManager: RecyclerView.LayoutManager,
                       @Nullable animatorListener: Animator.AnimatorListener?,
                       @Nullable animatorUpdateListener: ValueAnimator.AnimatorUpdateListener?) {
        val bounceAnimator = ValueAnimator.ofFloat(0f, 1f)
        bounceAnimator.addUpdateListener { valueAnimator ->
            // get update value
            val value = valueAnimator.animatedValue as Float

            for (info in views) {

                // left offset for view for current update value
                val left = (info.startLeft + value * (info.finishLeft - info.startLeft)).toInt()

                // right offset for view for current update value
                val right = (info.startRight + value * (info.finishRight - info.startRight)).toInt()

                // update view with new params
                layoutManager.layoutDecorated(info.view, left, info.top, right, info.bottom)
            }
            animatorUpdateListener?.onAnimationUpdate(valueAnimator)
        }

        bounceAnimator.duration = ANIMATION_SHIFT_VIEWS_DURATION.toLong()
        bounceAnimator.startDelay = (delay + ANIMATION_SHIFT_VIEWS_DELAY_THRESHOLD).toLong()
        if (animatorListener != null) {
            bounceAnimator.addListener(animatorListener)
        }
        bounceAnimator.start()
    }

    fun straightenView(view: View?, @Nullable listener: Animator.AnimatorListener?) {
        if (view != null) {
            val viewObjectAnimator = ObjectAnimator.ofFloat(view,
                    "rotation", view.rotation, 0f)
            viewObjectAnimator.duration = 150
            viewObjectAnimator.interpolator = DecelerateInterpolator()
            if (listener != null) {
                viewObjectAnimator.addListener(listener)
            }
            viewObjectAnimator.start()
        }
    }

    fun rotateView(view: View?, angle: Float, @Nullable listener: Animator.AnimatorListener?) {
        if (view != null) {
            val viewObjectAnimator = ObjectAnimator.ofFloat(view,
                    "rotation", view.rotation, angle)
            viewObjectAnimator.duration = 150
            viewObjectAnimator.interpolator = DecelerateInterpolator()
            if (listener != null) {
                viewObjectAnimator.addListener(listener)
            }
            viewObjectAnimator.start()
        }
    }
}