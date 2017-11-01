package assist.com.rehleg

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import assist.com.rehleg.ui.fragments.FeaturedVideosFragment
import assist.com.rehleg.ui.fragments.OtherVideosFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, FeaturedVideosFragment())
        transaction.add(R.id.other_videos_container, OtherVideosFragment())
        transaction.commit()

    }
}
