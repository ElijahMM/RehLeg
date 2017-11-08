package assist.com.rehleg.ui.fragments

import android.app.Activity
import android.app.Fragment
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.network.handlers.ErrorHandler
import assist.com.rehleg.network.handlers.ResponseHandler
import assist.com.rehleg.network.models.TopPromotedItemsList
import assist.com.rehleg.network.models.VideoItem
import assist.com.rehleg.ui.adapters.RecyclerViewBaseAdapter
import assist.com.rehleg.ui.holders.BaseViewHolder
import assist.com.rehleg.ui.holders.FeaturedVideoViewHolder
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate
import assist.com.rehleg.ui.views.recycler_view.ChildDrawingCallback
import assist.com.rehleg.ui.views.recycler_view.layout_manager.FVLMSettings
import assist.com.rehleg.ui.views.recycler_view.layout_manager.FeaturedVideosLayoutManager
import kotlinx.android.synthetic.main.fragment_featured_videos.*

class FeaturedVideosFragment : Fragment(), ResponseHandler.ResponseListener {

    var videoList: TopPromotedItemsList? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = container!!.inflate(R.layout.fragment_featured_videos)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        //Arc View initialisation
        arc_view.bottom = 40f

        //Recycler View initialisation
        val displayMetrics = DisplayMetrics()
        (activity as Activity).windowManager
                .defaultDisplay
                .getMetrics(displayMetrics)

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val width = displayMetrics.widthPixels * if (isLandscape) 0.35f else 0.6f

        val translationYDp = if (Utils.isTablet(activity)) 24f else 19f

        val layoutManagerSettings = FVLMSettings
                .newBuilder(activity)
                .withViewWidthPx(width.toInt())
                .withViewPivotY(40f)
                .withViewTranslationYStart(Utils.dpToPx(activity, translationYDp).toInt())
                .build()

        val layoutManager = FeaturedVideosLayoutManager(activity, layoutManagerSettings)
        val adapter = RecyclerViewBaseAdapter(FeaturedVideoViewHolder.Factory().setOnItemClickListener(
                object : BaseViewHolder.OnItemClickedListener {
                    override fun onItemClicked(view: View, position: Int) {
                        if (layoutManager.selectedItemPosition != position) {
                            layoutManager.switchItem(featured_video_recyclerView, position)
                        }
                    }
                }), videoList!!.TopPromotedItems)

                featured_video_recyclerView . layoutManager = layoutManager
                featured_video_recyclerView.itemAnimator = DefaultItemAnimator()
                featured_video_recyclerView . adapter = adapter
                featured_video_recyclerView.setChildDrawingOrderCallback(ChildDrawingCallback(layoutManager))
    }

    override fun onVideoListReceived(list: TopPromotedItemsList) {
        videoList = list
    }

    override fun onVideoReceived(item: VideoItem) {
    }

    override fun onErrorReceived(errorHandler: ErrorHandler) {
    }
}
