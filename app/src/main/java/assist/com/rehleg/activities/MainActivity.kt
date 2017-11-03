package assist.com.rehleg.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import assist.com.rehleg.R
import assist.com.rehleg.ui.fragments.FeaturedVideosFragment
import assist.com.rehleg.ui.fragments.OtherVideosFragment
import kotlinx.android.synthetic.main.activity_main.*

import assist.com.rehleg.ui.utils.Utils


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val of = OtherVideosFragment()
        val metrics = Utils.getDisplyMetrics(this)
        val workspaceHeight = metrics.heightPixels - Utils.getActionBarHeight(this)
        container.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, workspaceHeight / 2)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, FeaturedVideosFragment())
        transaction.add(R.id.other_videos_container, of)
        transaction.commit()
    }


}
