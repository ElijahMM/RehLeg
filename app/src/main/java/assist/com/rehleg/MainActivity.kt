package assist.com.rehleg

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import assist.com.rehleg.ui.fragments.FeaturedVideosFragment
import assist.com.rehleg.ui.fragments.OtherVideosFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val of = OtherVideosFragment()
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)

        container.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, metrics.heightPixels / 2)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, FeaturedVideosFragment())
        transaction.add(R.id.other_videos_container, of)
        transaction.commit()
    }


}
