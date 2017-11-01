package assist.com.rehleg.ui.views.recycler_view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by mihai on 30.10.2017.
 */
class GridSpacingItemDecoration(private val spanCount: Int = 0, private val spacing: Int = 0, private val includeEdge: Boolean = false, private val headerNum: Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val position = parent!!.getChildAdapterPosition(view) - headerNum // item position

        if (position >= 0) {
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect!!.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

//                if(column ==0){
//                    outRect.right = outRect.right + spacing/2
//                }else{
//                    outRect.left = outRect.left+ spacing/2
//                }

                if (position < spanCount) {
                    outRect.top = spacing / 2
                }
                outRect.bottom = spacing / 4
            }
        } else {
            outRect!!.left = 0
            outRect.right = 0
            outRect.top = 0
            outRect.bottom = 0
        }
    }
}