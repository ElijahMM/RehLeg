package assist.com.rehleg

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.assist.lego.testing.ui.fragments.FeaturedVideosFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, FeaturedVideosFragment())
        transaction.commit()
    }
}
