package assist.com.rehleg.ui.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by mihai on 31.10.2017.
 */
abstract class BaseViewHolder<in T>(viewItem: View) : RecyclerView.ViewHolder(viewItem) {

    interface Factory<in T> {
        fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>
    }

    interface OnItemClickedListener {
        fun onItemClicked(view:View, position: Int)
    }

    abstract fun onBindView(item: T)
}