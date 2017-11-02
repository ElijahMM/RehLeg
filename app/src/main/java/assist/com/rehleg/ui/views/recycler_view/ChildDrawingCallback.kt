package assist.com.rehleg.ui.views.recycler_view

import android.support.v7.widget.RecyclerView
import java.lang.ref.WeakReference

/**
 * Created by Sergiu on 02.11.2017.
 */
class ChildDrawingCallback(layoutManager: RecyclerView.LayoutManager): RecyclerView.ChildDrawingOrderCallback {

    private var mLayoutManagerWeakReference: WeakReference<RecyclerView.LayoutManager> = WeakReference(layoutManager)

    override fun onGetChildDrawingOrder(childCount: Int, i: Int): Int {
        val layoutManager = mLayoutManagerWeakReference.get()
        if (layoutManager != null) {
            val startView = layoutManager.getChildAt(0)
            val position = layoutManager.getPosition(startView)

            val isStartFromBelow: Boolean

            isStartFromBelow = position % 2 == 0

            val result: Int
            if (isStartFromBelow) {
                result = if (i % 2 == 0) {
                    //front
                    if (i == 0) 0 else i - 1
                } else {
                    //bellow
                    if (i + 1 >= childCount) i else i + 1
                }
            } else {
                result = if (i % 2 == 0) {
                    //front
                    if (i + 1 >= childCount) i else i + 1
                } else {
                    //bellow
                    i - 1
                }
            }
            return result
        }
        return i
    }
}