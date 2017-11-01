package assist.com.rehleg.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.inflate
import kotlinx.android.synthetic.main.other_video_item.view.*

/**
 * Created by mihai on 30.10.2017.
 */
class OtherVideosAdapter(val context: Context, val isTablet: Boolean = false) : RecyclerView.Adapter<OtherVideosAdapter.OtherVideosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OtherVideosViewHolder = OtherVideosViewHolder(parent?.inflate(R.layout.other_video_item)!!)

    override fun onBindViewHolder(holder: OtherVideosViewHolder?, position: Int) {
        holder?.bindView()
    }

    override fun getItemCount(): Int = 10


    inner class OtherVideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView() = with(itemView) {
            if (!isTablet) {
                element_shadow.setBackgroundColor(ContextCompat.getColor(context, R.color.otherVideosBkgColor))
            }
        }
    }

}