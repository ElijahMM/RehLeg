package assist.com.rehleg.ui.views.recycler_view

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View


/**
 * Created by Sergiu on 30.10.2017.
 */
class SimpleItemDecorator : RecyclerView.ItemDecoration {

    private var space: Int = 0
    private var isHorizontalLayout: Boolean = false
    private var marginLeft: Int = 0

    constructor(context: Context, space: Int) {
        init(context, space, false)
    }

    constructor(context: Context, space: Int, isHorizontalLayout: Boolean) {
        init(context, space, isHorizontalLayout)
    }

    private fun init(context: Context, space: Int, isHorizontalLayout: Boolean) {
        this.space = space
        this.isHorizontalLayout = isHorizontalLayout

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager
                .defaultDisplay
                .getMetrics(displayMetrics)

        marginLeft = displayMetrics.widthPixels / 5
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (isHorizontalLayout) {
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.left = 0
            else
                outRect.left = space
        } else {
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = 0
            else
                outRect.top = space
        }
    }
}