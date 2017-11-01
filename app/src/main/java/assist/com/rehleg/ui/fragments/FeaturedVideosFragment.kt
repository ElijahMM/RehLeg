package assist.com.rehleg.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.Utils
import assist.com.rehleg.ui.utils.inflate
import assist.com.rehleg.ui.views.recycler_view.SimpleItemDecorator
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
        val params = featured_video_recyclerView.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = 48

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager
                .defaultDisplay
                .getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels * 0.6f

        val layoutManagerSettings = FVLMSettings
                .newBuilder(activity)
                .withViewWidthPx(width.toInt())
                .build()

        featured_video_recyclerView.setHasFixedSize(false)
        featured_video_recyclerView.addItemDecoration(SimpleItemDecorator(activity, Utils.dpToPx(activity, 16f).toInt(), true))

        featured_video_recyclerView.layoutManager = FeaturedVideosLayoutManager(activity, layoutManagerSettings)
        featured_video_recyclerView.adapter = FeaturedVideosAdapter(activity)
    }
}
