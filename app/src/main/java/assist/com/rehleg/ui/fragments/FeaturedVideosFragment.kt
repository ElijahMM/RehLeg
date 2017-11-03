package assist.com.rehleg.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.activities.DialogActivity
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate
import assist.com.rehleg.ui.views.recycler_view.ChildDrawingCallback
import assist.com.rehleg.ui.views.recycler_view.layout_manager.FVLMSettings
import assist.com.rehleg.ui.views.recycler_view.layout_manager.FeaturedVideosLayoutManager
import com.assist.lego.testing.ui.adapters.FeaturedVideosAdapter
import com.kogitune.activity_transition.ActivityTransitionLauncher
import kotlinx.android.synthetic.main.featured_video_item.view.*
import kotlinx.android.synthetic.main.fragment_featured_videos.*

class FeaturedVideosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = container!!.inflate(R.layout.fragment_featured_videos)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        //Arc View initialisation
        arc_view.bottom = 40f

        //Recycler View initialisation
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager
                .defaultDisplay
                .getMetrics(displayMetrics)

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val width =  displayMetrics.widthPixels * if (isLandscape) 0.35f else 0.6f

        val translationYDp = if (Utils.isTablet(activity)) 24f else 19f

        val layoutManagerSettings = FVLMSettings
                .newBuilder(activity)
                .withViewWidthPx(width.toInt())
                .withViewPivotY(40f)
                .withViewTranslationYStart(Utils.dpToPx(activity, translationYDp).toInt())
                .build()

        val layoutManager = FeaturedVideosLayoutManager(activity, layoutManagerSettings)
        val adapter = FeaturedVideosAdapter(activity)

        featured_video_recyclerView.layoutManager = layoutManager
        featured_video_recyclerView.itemAnimator = DefaultItemAnimator()
        featured_video_recyclerView.adapter = adapter
        featured_video_recyclerView.setChildDrawingOrderCallback(ChildDrawingCallback(layoutManager))

        adapter.setOnItemClickListener(object: FeaturedVideosAdapter.OnItemClickListener {
            override fun onItemClicked(pos: Int, view: View) {
                if (layoutManager.selectedItemPosition != pos) {
                    layoutManager.switchItem(featured_video_recyclerView, pos)
                }else{
                    val intent = Intent(activity, DialogActivity::class.java)
                    ActivityTransitionLauncher.with(activity).from(view.preview_videoView).launch(intent)
                }
            }
        })
    }
}
