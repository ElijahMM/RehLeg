package assist.com.rehleg.ui.adapters

import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.inflate

/**
 * Created by mihai on 31.10.2017.
 */
class OtherVideosPhoneViewHolder private constructor(itemView: View) : BaseViewHolder<String>(itemView) {

    class Factory : BaseViewHolder.Factory<String> {
        override fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> = OtherVideosPhoneViewHolder(parent.inflate(R.layout.other_video_phone_item))
    }


    override fun onBindView(item: String) {
    }


}