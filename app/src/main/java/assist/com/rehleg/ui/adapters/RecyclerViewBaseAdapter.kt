package assist.com.rehleg.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by mihai on 30.10.2017.
 */
class RecyclerViewBaseAdapter<T>(private var factory: BaseViewHolder.Factory<T>, private var items: MutableList<T>) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun onBindViewHolder(holder: BaseViewHolder<T>?, position: Int) {
        holder?.onBindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<T> = factory.createViewHolder(parent!!, viewType)


    fun setItems(items: MutableList<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addNewItem(item: T) {
        this.items.add(item)
        notifyItemChanged(itemCount - 1)
    }

    fun removeAtPosition(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}