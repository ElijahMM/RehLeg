package assist.com.rehleg.ui.views.recycler_view.animation

import android.view.View

/**
 * Created by Sergiu on 31.10.2017.
 */
data class ViewAnimationInfo(
    var startLeft: Int = 0,
    var finishLeft: Int = 0,
    var startRight: Int = 0,
    var finishRight: Int = 0,
    var top: Int = 0,
    var bottom: Int = 0,
    var view: View? = null
)