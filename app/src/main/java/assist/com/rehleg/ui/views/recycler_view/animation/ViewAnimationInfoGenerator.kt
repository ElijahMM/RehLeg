package assist.com.rehleg.ui.views.recycler_view.animation

import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by Sergiu on 31.10.2017.
 */
internal object ViewAnimationInfoGenerator {

    /**
     * Generate collection for open/close or shift animations.
     *
     * @param delta         delta x (shift distance) for views
     * @param isSelected    flag if have selected item
     * @param layoutManager the layout manager
     * @param centerViewPos the center view position
     * @param isCollapsed   flag if have collapsed items
     * @return collection of view data
     */
    fun generate(delta: Int,
                 isSelected: Boolean,
                 @NonNull layoutManager: RecyclerView.LayoutManager,
                 centerViewPos: Int,
                 isCollapsed: Boolean): Collection<ViewAnimationInfo> {

        val infoViews = ArrayList<ViewAnimationInfo>()
        if (centerViewPos == RecyclerView.NO_POSITION) {
            return infoViews
        }
        // +++++ prepare data +++++
        var view: View
        var viewPosition: Int
        var info: ViewAnimationInfo
        var isSelectedKoef: Int
        var collapseKoef: Int
        // ----- prepare data -----


        val count = layoutManager.childCount
        var i = 0
        while (i < count) {
            view = layoutManager.getChildAt(i)
            viewPosition = layoutManager.getPosition(view)
            if (viewPosition == centerViewPos) {
                i++
                continue
            }
            info = ViewAnimationInfo()
            info.view = view
            info.startLeft = layoutManager.getDecoratedLeft(view)
            info.startRight = layoutManager.getDecoratedRight(view)
            info.top = layoutManager.getDecoratedTop(view)
            info.bottom = layoutManager.getDecoratedBottom(view)
            if (viewPosition < centerViewPos) {
                // left view

                // show views with overlapping if have selected item.
                isSelectedKoef = if (isSelected) -1 else 1

                // make distance between each item if isCollapsed = true
                collapseKoef = if (isCollapsed) centerViewPos - viewPosition else 1

                info.finishLeft = info.startLeft + delta * isSelectedKoef * collapseKoef
                info.finishRight = info.startRight + delta * isSelectedKoef * collapseKoef
            } else {
                // right view

                // show views with overlapping if have selected item.
                isSelectedKoef = if (isSelected) 1 else -1

                // make distance between each item if isCollapsed = true
                collapseKoef = if (isCollapsed) viewPosition - centerViewPos else 1

                info.finishLeft = info.startLeft + delta * isSelectedKoef * collapseKoef
                info.finishRight = info.startRight + delta * isSelectedKoef * collapseKoef
            }

            infoViews.add(info)
            i++
        }
        return infoViews
    }
}