package assist.com.rehleg.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.network.handlers.ErrorHandler
import assist.com.rehleg.network.handlers.ResponseHandler
import assist.com.rehleg.network.models.TopPromotedItemsList
import assist.com.rehleg.network.models.VideoItem
import assist.com.rehleg.ui.adapters.*
import assist.com.rehleg.ui.holders.BaseViewHolder
import assist.com.rehleg.ui.holders.OtherVideosPhoneViewHolder
import assist.com.rehleg.ui.holders.OtherVideosTabletViewHolder
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate
import assist.com.rehleg.ui.views.recycler_view.GridSpacingItemDecoration
import assist.com.rehleg.ui.views.recycler_view.layout_manager.NoScrollGridLayoutManager
import kotlinx.android.synthetic.main.fragment_other_videos.*
import com.kogitune.activity_transition.ActivityTransitionLauncher
import android.content.Intent
import assist.com.rehleg.activities.DialogActivity
import kotlinx.android.synthetic.main.other_video_phone_item.view.*
import kotlinx.android.synthetic.main.other_video_tablet_item.view.*


/**
 * Created by mihai on 30.10.2017.
 */
class OtherVideosFragment : Fragment(), ResponseHandler.ResponseListener {



    private lateinit var tabletLayoutManager: NoScrollGridLayoutManager
    private lateinit var tabletAdapter: RecyclerViewBaseAdapter<String>
    private var landscapeItemDecoration = GridSpacingItemDecoration(2, 200, true)

    private lateinit var phoneAdapter: RecyclerViewBaseAdapter<String>
    private lateinit var phoneLayoutManager: NoScrollGridLayoutManager
    private var portraitItemDecoration = GridSpacingItemDecoration(2, 150, true)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View = container!!.inflate(R.layout.fragment_other_videos)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ResponseHandler.setResponseListener(this)
        initComponents()

    }


    private fun initComponents() {
        val list = mutableListOf<String>()
        (1..20).forEach {
            list.add("s")
        }

        initAdapters(list)

        if (Utils.isTablet(activity)) {
            initTabletLayout()
        } else {
            otherVideosRecycler_View.adapter = phoneAdapter
            otherVideosRecycler_View.layoutManager = phoneLayoutManager
        }

    }

    private fun initTabletLayout() {
        otherVideosRecycler_View.setHasFixedSize(false)
        if (activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            otherVideosRecycler_View.layoutManager = tabletLayoutManager
            otherVideosRecycler_View.adapter = tabletAdapter
            otherVideosRecycler_View.removeItemDecoration(portraitItemDecoration)
            otherVideosRecycler_View.addItemDecoration(landscapeItemDecoration)
        } else {
            otherVideosRecycler_View.layoutManager = tabletLayoutManager
            otherVideosRecycler_View.adapter = tabletAdapter
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

    private fun initAdapters(list: MutableList<String>) {
        tabletAdapter = RecyclerViewBaseAdapter(
                OtherVideosTabletViewHolder.Factory().setOnItemClickedListener(object : BaseViewHolder.OnItemClickedListener<String> {
                    override fun onItemClicked(view: View, position: Int) {
                        openPreviewDialog(view.other_tablet_videos_player)
                    }

                }), list)

        tabletLayoutManager = NoScrollGridLayoutManager(activity, 2)
        tabletLayoutManager.setScrollEnabled(false)

        phoneAdapter = RecyclerViewBaseAdapter(OtherVideosPhoneViewHolder.Factory().setOnItemClickedListener(object : BaseViewHolder.OnItemClickedListener<String> {
            override fun onItemClicked(view: View, position: Int) {
                openPreviewDialog(view.other_videos_phone_player)
            }

        }), list)

        phoneLayoutManager = NoScrollGridLayoutManager(activity, 1)
        phoneLayoutManager.setScrollEnabled(false)
    }

    private fun openPreviewDialog(view: View) {
        val intent = Intent(activity, DialogActivity::class.java)
        ActivityTransitionLauncher.with(activity).from(view).launch(intent)
    }

    override fun onVideoListReceived(list: TopPromotedItemsList) {
    }

    override fun onVideoReceived(item: VideoItem) {
        Log.w("Video", "Vide")
    }

    override fun onErrorReceived(errorHandler: ErrorHandler) {
        Log.w(errorHandler.errorType, errorHandler.errorMessage)
    }
}

