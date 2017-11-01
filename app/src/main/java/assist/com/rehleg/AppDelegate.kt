package assist.com.rehleg

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by mihai on 01.11.2017.
 */

class AppDelegate : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        fun getMyApplicationContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }
}

