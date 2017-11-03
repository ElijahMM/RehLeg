package assist.com.rehleg.ui.holders

import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.inflate

/**
 * Created by Sergiu on 03.11.2017.
 */
class FeaturedVideoViewHolder(view: View) : BaseViewHolder<String>(view) {

    class Factory : BaseViewHolder.Factory<String> {
        lateinit var itemClickListener: BaseViewHolder.OnItemClickedListener

        override fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
            val holder = FeaturedVideoViewHolder(parent.inflate(R.layout.featured_video_item))
            holder.itemView.setOnClickListener({view -> itemClickListener.onItemClicked(view, holder.adapterPosition)})
            return holder
        }

        fun setOnItemClickListener(itemClickListener: OnItemClickedListener) {
            this.itemClickListener = itemClickListener
        }
    }

    override fun onBindView(item: String) {
    }
}