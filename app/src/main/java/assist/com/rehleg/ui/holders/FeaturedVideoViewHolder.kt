package assist.com.rehleg.ui.holders

import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.network.models.TopPromotedItem
import assist.com.rehleg.ui.utils.inflate

/**
 * Created by Sergiu on 03.11.2017.
 */
class FeaturedVideoViewHolder(view: View) : BaseViewHolder<TopPromotedItem>(view) {

    class Factory : BaseViewHolder.Factory<TopPromotedItem> {
        lateinit var itemClickListener: BaseViewHolder.OnItemClickedListener

        override fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TopPromotedItem> {
            val holder = FeaturedVideoViewHolder(parent.inflate(R.layout.featured_video_item))
            holder.itemView.setOnClickListener({view -> itemClickListener.onItemClicked(view, holder.adapterPosition)})
            return holder
        }

        fun setOnItemClickListener(itemClickListener: OnItemClickedListener): Factory {
            this.itemClickListener = itemClickListener
            return this
        }
    }

    override fun onBindView(item: assist.com.rehleg.network.models.TopPromotedItem) {

    }
}