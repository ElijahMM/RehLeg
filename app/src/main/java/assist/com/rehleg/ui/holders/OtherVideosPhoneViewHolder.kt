package assist.com.rehleg.ui.holders

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate

/**
 * Created by mihai on 31.10.2017.
 */
class OtherVideosPhoneViewHolder private constructor(itemView: View) : BaseViewHolder<String>(itemView) {

    class Factory : BaseViewHolder.Factory<String> {
        private lateinit var onItemClicked: OnItemClickedListener

        override fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
            val view = parent.inflate(R.layout.other_video_phone_item)
            val metrics = Utils.getDisplyMetrics(parent.context)
            val viewHeight = (metrics.heightPixels.toFloat() - Utils.getActionBarHeight(parent.context)) / 2 - Utils.dpToPx(parent.context, 48f)

            view.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, viewHeight.toInt())
            val holder = OtherVideosPhoneViewHolder (view)
            view.setOnClickListener { run { onItemClicked.onItemClicked(view, holder.adapterPosition) } }
            return holder
        }

        fun setOnItemClickedListener(onItemClickedListener: OnItemClickedListener): OtherVideosPhoneViewHolder.Factory {
            this.onItemClicked = onItemClickedListener
            return this
        }
    }


    override fun onBindView(item: String) {
    }


}