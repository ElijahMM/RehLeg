package assist.com.rehleg.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.LinearLayout
import assist.com.rehleg.R
import assist.com.rehleg.ui.fragments.FeaturedVideosFragment
import assist.com.rehleg.ui.fragments.OtherVideosFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        val weight = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.65 else 0.5
        val height = (metrics.heightPixels * weight).toInt()
        featured_videos_container.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)

        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.featured_videos_container, FeaturedVideosFragment())
        transaction.add(R.id.other_videos_container, OtherVideosFragment())
        transaction.commit()


    }
}
