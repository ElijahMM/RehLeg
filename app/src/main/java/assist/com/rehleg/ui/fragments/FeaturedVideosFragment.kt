package assist.com.rehleg.ui.fragments

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.inflate
import com.assist.lego.testing.ui.adapters.FeaturedVideosAdapter
import kotlinx.android.synthetic.main.fragment_featured_videos.*

class FeaturedVideosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = container!!.inflate(R.layout.fragment_featured_videos)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arc_view.bottom = 40f

        val params = featured_video_recyclerView.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = 48

        featured_video_recyclerView.setHasFixedSize(false)
        featured_video_recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        featured_video_recyclerView.adapter = FeaturedVideosAdapter(context)

    }
}
