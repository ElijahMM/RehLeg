package assist.com.rehleg.ui.fragments

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate
import assist.com.rehleg.ui.views.recycler_view.layout_manager.FVLMSettings
import assist.com.rehleg.ui.views.recycler_view.layout_manager.FeaturedVideosLayoutManager
import com.assist.lego.testing.ui.adapters.FeaturedVideosAdapter
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
        val width = if (isLandscape) displayMetrics.widthPixels * 0.35f else displayMetrics.widthPixels * 0.6f

        val translationYDp = if (Utils.isTablet(activity)) 25f else 19f

        val layoutManagerSettings = FVLMSettings
                .newBuilder(activity)
                .withViewWidthPx(width.toInt())
                .withViewPivotY(40f)
                .withViewTranslationYStart(Utils.dpToPx(activity, translationYDp).toInt())
                .build()

        featured_video_recyclerView.setHasFixedSize(false)

        featured_video_recyclerView.layoutManager = FeaturedVideosLayoutManager(activity, layoutManagerSettings)
        featured_video_recyclerView.adapter = FeaturedVideosAdapter(activity)
    }
}
