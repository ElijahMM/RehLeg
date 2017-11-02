package assist.com.rehleg

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import assist.com.rehleg.ui.animators.ViewAnimationUtils
import assist.com.rehleg.ui.fragments.FeaturedVideosFragment
import assist.com.rehleg.ui.fragments.OtherVideosFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OtherVideosFragment.ActionDone {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var of = OtherVideosFragment()
        of.setOnACtionDone(this)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, FeaturedVideosFragment())
        transaction.add(R.id.other_videos_container, of)
        transaction.commit()

    }

    override fun onActionDone() {
        Log.w("clicl", "CLick")
        ViewAnimationUtils.expand(other_videos_container)
    }
}
