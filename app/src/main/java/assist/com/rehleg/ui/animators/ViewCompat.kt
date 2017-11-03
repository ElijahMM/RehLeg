package assist.com.rehleg.ui.animators

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


/**
 * Created by mihai on 02.11.2017.
 */
class ViewCompat private constructor(private val mView: View?) : Animation() {
    private var mTranslationY: Float = 0.toFloat()

    init {
        duration = 0
        fillAfter = true
        mView!!.animation = this
    }

    fun setTranslationY(translationY: Int) {
        mTranslationY = translationY.toFloat()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        if (mView != null) {
            t.matrix.postTranslate(0f, mTranslationY)
        }
    }

    companion object {


        fun wrap(view: View): ViewCompat {
            val wrapper = view.animation
            return wrapper as? ViewCompat ?: ViewCompat(view)
        }
    }
}
