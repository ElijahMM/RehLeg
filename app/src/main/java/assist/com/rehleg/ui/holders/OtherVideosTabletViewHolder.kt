package assist.com.rehleg.ui.holders

import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate

/**
 * Created by mihai on 31.10.2017.
 */
class OtherVideosTabletViewHolder private constructor(itemView: View) : BaseViewHolder<String>(itemView) {


    class Factory : BaseViewHolder.Factory<String> {
        private lateinit var onItemClicked: OnItemClickedListener

        override fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
            val view = parent.inflate(R.layout.other_video_tablet_item)
            val metrics = Utils.getDisplyMetrics(parent.context)
            val widthDP = metrics.widthPixels.toFloat() / 3
            val ratio = widthDP / 4
            val heightDP = ratio * 3

            view.layoutParams = ViewGroup.LayoutParams(widthDP.toInt(), heightDP.toInt())
            val holder = OtherVideosTabletViewHolder(view)
            view.setOnClickListener { run { onItemClicked.onItemClicked(view, holder.adapterPosition) } }
            return holder

        }

        fun setOnItemClickedListener(onItemClickedListener: OnItemClickedListener): Factory {
            this.onItemClicked = onItemClickedListener
            return this
        }

    }


    override fun onBindView(item: String) {
    }


}