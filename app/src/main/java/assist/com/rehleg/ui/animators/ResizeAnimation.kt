package assist.com.rehleg.ui.animators

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


/**
 * Created by mihai on 02.11.2017.
 */
class ResizeAnimation(var view: View, private var targetHeight: Int, private var startHeight: Int = 0) : Animation() {


    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newHeight = targetHeight * interpolatedTime

        view.layoutParams.height = newHeight.toInt()
        view.requestLayout()
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}