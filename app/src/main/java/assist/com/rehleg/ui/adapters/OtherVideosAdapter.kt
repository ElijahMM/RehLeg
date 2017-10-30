package assist.com.rehleg.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by mihai on 30.10.2017.
 */
class OtherVideosAdapter(private val context: Context) : RecyclerView.Adapter<OtherVideosAdapter.OtherVieosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OtherVieosViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: OtherVieosViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = 10


    inner class OtherVieosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView() = with(itemView) {

        }
    }

}