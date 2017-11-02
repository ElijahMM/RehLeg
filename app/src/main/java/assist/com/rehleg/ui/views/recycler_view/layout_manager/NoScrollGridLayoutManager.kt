package assist.com.rehleg.ui.views.recycler_view.layout_manager

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet

/**
 * Created by mihai on 02.11.2017.
 */
class NoScrollGridLayoutManager(context: Context, spanCount: Int) : GridLayoutManager(context, spanCount) {

    private var isScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}