package assist.com.rehleg.activities

import android.app.Activity
import android.os.Bundle
import assist.com.rehleg.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import assist.com.rehleg.ui.utils.Utils
import com.kogitune.activity_transition.ActivityTransition
import com.kogitune.activity_transition.ExitActivityTransition
import kotlinx.android.synthetic.main.activity_video_preview.*


/**
 * Created by mihai on 03.11.2017.
 */
class DialogActivity : Activity() {

    private var exitTransition: ExitActivityTransition? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_preview)
        this.setFinishOnTouchOutside(true)

        initComponents(savedInstanceState)
    }

    private fun initComponents(savedInstanceState: Bundle?) {
        val metrics = Utils.getDisplyMetrics(this)
        val width = 3.5 * (metrics.widthPixels / 4)
        val height = metrics.heightPixels / 2.5

        val lp = RelativeLayout.LayoutParams(width.toInt(), height.toInt())
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        preview_rootView.layoutParams = lp
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawable(ColorDrawable(0))

        exitTransition = ActivityTransition
                .with(intent)
                .enterListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        Log.d("TAG", "onEnterAnimationEnd!!")
                    }

                    override fun onAnimationStart(animation: Animator) {
                        Log.d("TAG", "onOEnterAnimationStart!!")
                    }
                })
                .to(findViewById<View>(R.id.preview_rootView))
                .start(savedInstanceState)
        exitTransition!!.exitListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                Log.d("TAG", "onOutAnimationStart!!")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.d("TAG", "onOutAnimationEnd!!")
            }
        })

        dialog_root_view.setOnClickListener { run { exitTransition!!.exit(this) } }
    }

    override fun onBackPressed() {
        exitTransition!!.exit(this)
    }
}