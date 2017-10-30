package assist.com.rehleg.ui.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by mihai on 30.10.2017.
 */

fun ViewGroup.inflate(@LayoutRes layoutID: Int, attachedToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layoutID, this, attachedToRoot)

fun ImageView.loadUrl(photoUrl: String) {
    Picasso.with(context).load(photoUrl).into(this)
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.isShowed():Boolean = this.visibility == View.VISIBLE
