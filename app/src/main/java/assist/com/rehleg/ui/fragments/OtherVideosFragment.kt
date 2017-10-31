package assist.com.rehleg.ui.fragments

import android.content.res.Configuration
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.adapters.GridSpacingItemDecoration
import assist.com.rehleg.ui.adapters.OtherVideosAdapter
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate
import kotlinx.android.synthetic.main.fragment_other_videos.*

/**
 * Created by mihai on 30.10.2017.
 */
class OtherVideosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View = container!!.inflate(R.layout.fragment_other_videos)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Utils.isTablet(activity)) {
            otherVideosRecycler_View.layoutManager = GridLayoutManager(activity, 2)
            otherVideosRecycler_View.adapter = OtherVideosAdapter(activity)
            otherVideosRecycler_View.addItemDecoration(GridSpacingItemDecoration(2,200,true))
        } else {
            otherVideosRecycler_View.layoutManager = GridLayoutManager(activity, 1)
            otherVideosRecycler_View.adapter = OtherVideosAdapter(activity)
        }
    }
}

