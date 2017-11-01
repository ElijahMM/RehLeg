package assist.com.rehleg.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.adapters.*
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate
import kotlinx.android.synthetic.main.fragment_other_videos.*

/**
 * Created by mihai on 30.10.2017.
 */
class OtherVideosFragment : Fragment() {

    var list = mutableListOf<String>()
    private var landscapeItemDecoration = GridSpacingItemDecoration(2, 100, true)
    private var portraitItemDecoration = GridSpacingItemDecoration(2, 25, true)

    private lateinit var mLayoutManager: GridLayoutManager
    private lateinit var adapter: RecyclerViewBaseAdapter<String>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View = container!!.inflate(R.layout.fragment_other_videos)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        (1..20).forEach {
            list.add("s")
        }

        adapter = RecyclerViewBaseAdapter(
                OtherVideosTabletViewHolder.Factory().setOnItemClickedListener(object : BaseViewHolder.OnItemClickedListener<String> {
                    override fun onItemClicked(item: String, position: Int) {
                    }

                }), list)

        mLayoutManager = GridLayoutManager(activity, 2)
        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = if (position ==0) {
              2
            } else {
              1
            }

        }
        if (Utils.isTablet(activity)) {
            initTabletLayout()
        } else {
            otherVideosRecycler_View.layoutManager = GridLayoutManager(activity, 1)
            otherVideosRecycler_View.adapter = RecyclerViewBaseAdapter(OtherVideosPhoneViewHolder.Factory(), list)
        }
    }

    private fun initTabletLayout() {
        if (activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            otherVideosRecycler_View.layoutManager = mLayoutManager
            otherVideosRecycler_View.adapter = adapter
            otherVideosRecycler_View.removeItemDecoration(portraitItemDecoration)
            otherVideosRecycler_View.addItemDecoration(landscapeItemDecoration)
        } else {
            otherVideosRecycler_View.layoutManager = mLayoutManager
            otherVideosRecycler_View.adapter = adapter
            otherVideosRecycler_View.removeItemDecoration(landscapeItemDecoration)
            otherVideosRecycler_View.addItemDecoration(portraitItemDecoration)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (Utils.isTablet(activity)) {
            initTabletLayout()
        }

    }
}

