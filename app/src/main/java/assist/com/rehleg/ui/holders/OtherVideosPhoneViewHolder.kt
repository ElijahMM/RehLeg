package assist.com.rehleg.ui.holders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate

/**
 * Created by mihai on 31.10.2017.
 */
class OtherVideosPhoneViewHolder private constructor(itemView: View, private val onItemClickedListener: OnItemClickedListener<String>) : BaseViewHolder<String>(itemView) {

    class Factory : BaseViewHolder.Factory<String> {
        private lateinit var onItemClicked: OnItemClickedListener<String>

        override fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
            val view = parent.inflate(R.layout.other_video_phone_item)
            val metrics = Utils.getDisplyMetrics(parent.context)
            val viewHeight = (metrics.heightPixels.toFloat() - Utils.getActionBarHeight(parent.context)) / 2 - Utils.dpToPx(parent.context, 48f)


            view.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, viewHeight.toInt())
            return OtherVideosPhoneViewHolder(view, onItemClicked)
        }

        fun setOnItemClickedListener(onItemClickedListener: OnItemClickedListener<String>): OtherVideosPhoneViewHolder.Factory {
            this.onItemClicked = onItemClickedListener
            return this
        }
    }


    override fun onBindView(item: String) {
        onItemClickedListener.onItemClicked(item, adapterPosition)
    }


}