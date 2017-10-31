package assist.com.rehleg.ui.adapters

import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.inflate

/**
 * Created by mihai on 31.10.2017.
 */
class OtherVideosTabletViewHolder private constructor(itemView: View, private val onItemClickedListener: OnItemClickedListener<String>) : BaseViewHolder<String>(itemView) {

    companion object {
        val SMALL_VIEW_TYPE = 1
        var LARGE_VIEW_TYPE = 2
    }

    class Factory : BaseViewHolder.Factory<String> {
        private lateinit var onItemClicked: OnItemClickedListener<String>

        override fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> = if (viewType == SMALL_VIEW_TYPE)
            OtherVideosTabletViewHolder(parent.inflate(R.layout.other_video_tablet_item), onItemClicked)
        else
            OtherVideosTabletViewHolder(parent.inflate(R.layout.other_video_tabler_expanded_item), onItemClicked)


        fun setOnItemClickedListener(onItemClickedListener: OnItemClickedListener<String>): Factory {
            this.onItemClicked = onItemClickedListener
            return this
        }

    }



    override fun onBindView(item: String) {
        onItemClickedListener.onItemClicked(item, adapterPosition)
    }


}